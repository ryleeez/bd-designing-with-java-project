package com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.datastore.PackagingDatastore;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.NoPackagingFitsItemException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.UnknownFulfillmentCenterException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Box;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.PolyBag;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackagingDAOTest {

    private Item testItem = createItem("30", "30", "30");
    // 1900cc for 2000 and 10000 PolyBags in IAD2
    private Item mediumItem = createItem("19", "10", "10");
    private Item smallItem = createItem("5", "5", "5");
    // 4900cc for 5000cc packaging option in IND1
    private Item polyBag4900cc = createItem("10", "10", "49");

    private FulfillmentCenter ind1 = new FulfillmentCenter("IND1");
    private FulfillmentCenter abe2 = new FulfillmentCenter("ABE2");
    private FulfillmentCenter iad2 = new FulfillmentCenter("IAD2");

    private PackagingDatastore datastore = new PackagingDatastore();

    private PackagingDAO packagingDAO;

    @Test
    public void findShipmentOptions_unknownFulfillmentCenter_throwsUnknownFulfillmentCenterException() {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter("nonExistentFcCode");

        // WHEN + THEN
        assertThrows(UnknownFulfillmentCenterException.class, () -> {
            packagingDAO.findShipmentOptions(testItem, fulfillmentCenter);
        }, "When asked to ship from an unknown fulfillment center, throw UnknownFulfillmentCenterException.");
    }

    @Test
    public void findShipmentOptions_packagingDoesntFit_throwsNoPackagingFitsItemException() {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN + THEN
        assertThrows(NoPackagingFitsItemException.class, () -> {
            packagingDAO.findShipmentOptions(testItem, ind1);
        }, "When no packaging can fit the item, throw NoPackagingFitsItemException.");
    }

    @Test
    public void findShipmentOptions_onePackagingAvailableAndFits_singlePackaging() throws Exception {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN
        List<ShipmentOption> shipmentOptions = packagingDAO.findShipmentOptions(polyBag4900cc, ind1);

        // THEN
        assertEquals(1, shipmentOptions.size(),
            "When fulfillment center has packaging that can fit item, return a ShipmentOption with the item, "
                + "fulfillment center, and packaging that can fit the item.");
    }

    @Test
    public void findShipmentOptions_twoPackagingAvailableAndOneFits_singlePackaging() throws Exception {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN
        List<ShipmentOption> shipmentOptions = packagingDAO.findShipmentOptions(testItem, abe2);

        // THEN
        assertEquals(1, shipmentOptions.size(),
            "When fulfillment center has packaging that can fit item, return a ShipmentOption with the item, "
                + "fulfillment center, and packaging that can fit the item.");
    }

    @Test
    public void findShipmentOptions_twoPackagingAvailableAndBothFit_twoPackagingOptions() throws Exception {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN
        List<ShipmentOption> shipmentOptions = packagingDAO.findShipmentOptions(smallItem, abe2);

        // THEN
        assertEquals(4, shipmentOptions.size(),
            "When fulfillment center has multiple packaging that can fit item, return a ShipmentOption "
                + "for each.");
    }

    @Test
    public void findShipmentOptions_withPolyBagOptions_returnsAllValidOptions() throws Exception {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN
        List<ShipmentOption> shipmentOptions = packagingDAO.findShipmentOptions(mediumItem, iad2);

        // THEN
        assertEquals(4, shipmentOptions.size(),
            "When fulfillment center has multiple packaging that can fit item, return a ShipmentOption "
                + "for each.");
        int boxCount = 0;
        int bagCount = 0;

        for(ShipmentOption option : shipmentOptions) {
            if (option.getPackaging().getClass().equals(Box.class)) {
                boxCount++;
            }

            if (option.getPackaging().getClass().equals(PolyBag.class)) {
                bagCount++;
            }
        }

        assertEquals(1, boxCount, "Expected a single Box packaging to fit item " + mediumItem);
        assertEquals(3, bagCount, "Expected 3 PolyBag packaging to fit item " + mediumItem);
    }

    @Test
    public void findShipmentOptions_duplicatePackagingInDatastore_returnSingleShipmentOption() throws Exception {
        // GIVEN
        packagingDAO = new PackagingDAO(datastore);

        // WHEN
        List<ShipmentOption> shipmentOptions = packagingDAO.findShipmentOptions(smallItem, iad2);

        // THEN
        // we have 5 packaging options for IAD2, only 4 are unique
        assertEquals(4, shipmentOptions.size(),
            "When fulfillment center has duplicate packaging that can fit item, return a single ShipmentOption");
    }

    private Item createItem(String length, String width, String height) {
        return Item.builder()
                .withAsin("B00TEST")
                .withDescription("Test Item")
                .withHeight(new BigDecimal(length))
                .withWidth(new BigDecimal(width))
                .withLength(new BigDecimal(height))
                .build();
    }
}
