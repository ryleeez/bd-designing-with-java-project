package com.amazon.ata.dao;

import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.FcPackagingOption;
import com.amazon.ata.types.FulfillmentCenter;
import com.amazon.ata.types.Item;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentOption;

import java.util.*;

/**
 * Access data for which packaging is available at which fulfillment center.
 */
public class PackagingDAO {
    /**
     * A map of fulfillment centers with a packaging options they provide.
     */
    private Map<FulfillmentCenter, Set<Packaging>> fcPackagingOptionsMap;

    /**
     * Instantiates a PackagingDAO object.
     * @param datastore Where to pull the data from for fulfillment center/packaging available mappings.
     */
    public PackagingDAO(PackagingDatastore datastore) {
        this.fcPackagingOptionsMap =  new HashMap<>();
        for (FcPackagingOption option : datastore.getFcPackagingOptions()) {
            FulfillmentCenter fc = option.getFulfillmentCenter();
            Packaging packaging = option.getPackaging();
            Set<Packaging> packagingSet = fcPackagingOptionsMap.computeIfAbsent(fc, k -> new HashSet<>());
            packagingSet.removeIf(p -> p.equals(packaging));

            // Add the new packaging
            packagingSet.add(packaging);
            System.out.println("Added/Updated packaging for " + fc.getFcCode() + ": " + packaging.getClass().getSimpleName());
        }
    }

    /**
     * Returns the packaging options available for a given item at the specified fulfillment center. The API
     * used to call this method handles null inputs, so we don't have to.
     *
     * @param item the item to pack
     * @param fulfillmentCenter fulfillment center to fulfill the order from
     * @return the shipping options available for that item; this can never be empty, because if there is no
     * acceptable option an exception will be thrown
     * @throws UnknownFulfillmentCenterException if the fulfillmentCenter is not in the fcPackagingOptions list
     * @throws NoPackagingFitsItemException if the item doesn't fit in any packaging at the FC
     */
    public List<ShipmentOption> findShipmentOptions(Item item, FulfillmentCenter fulfillmentCenter)
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

        Set<Packaging> fcOptions = fcPackagingOptionsMap.get(fulfillmentCenter);
        if (fcOptions == null) {
            throw new UnknownFulfillmentCenterException(
                    String.format("Unknown FC: %s!", fulfillmentCenter.getFcCode()));
        }

        // Check all FcPackagingOptions for a suitable Packaging in the given FulfillmentCenter
        Set<ShipmentOption> result = new HashSet<>();
        for (Packaging packaging : fcOptions) {
            if (packaging.canFitItem(item)) {
                ShipmentOption option = ShipmentOption.builder()
                        .withItem(item)
                        .withPackaging(packaging)
                        .withFulfillmentCenter(fulfillmentCenter)
                        .build();
                result.add(option);

            }
        }

        // Notify caller about unexpected results
        if (result.isEmpty()) {
            throw new NoPackagingFitsItemException(
                    String.format("No packaging at %s fits %s!", fulfillmentCenter.getFcCode(), item));
        }

        return new ArrayList<>(result);
    }
}
