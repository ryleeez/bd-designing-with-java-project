package com.amazon.atacurriculumsustainabilityshipmentservicelambda.service;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.SustainabilityShipmentClientException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao.PackagingDAO;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.NoPackagingFitsItemException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.UnknownFulfillmentCenterException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is responsible for finding the appropriate shipment option from all available options returned
 * by the PackagingDAO.
 */
public class ShipmentService {
    private final Logger log = LogManager.getLogger();

    /**
     * PackagingDAO is used to retrieve all valid shipment options for a given fulfillment center and item.
     */
    private PackagingDAO packagingDAO;

    /**
     * A CostStrategy used to calculate the relative cost of a ShipmentOption.
     */
    private CostStrategy costStrategy;

    /**
     * Instantiates a new ShipmentService object.
     * @param packagingDAO packaging data access object used to retrieve all available shipment options
     * @param costStrategy cost strategy used to calculate the relative cost of a shipment option
     */
    public ShipmentService(PackagingDAO packagingDAO, CostStrategy costStrategy) {
        this.packagingDAO = packagingDAO;
        this.costStrategy = costStrategy;
    }
    /**
     * Finds the shipment option for the given item and fulfillment center with the lowest cost.
     *
     * @param item the item to package
     * @param fulfillmentCenter fulfillment center in which to look for the packaging
     * @return the lowest cost shipment option for the item and fulfillment center, or
     *         a shipment option with null packaging if none will fit the item
     */
    public ShipmentOption findShipmentOption(final Item item, final FulfillmentCenter fulfillmentCenter) {
        log.info("Got {}, {}", item.toString(), fulfillmentCenter.toString());
        try {
            List<ShipmentOption> results = this.packagingDAO.findShipmentOptions(item, fulfillmentCenter);
            return getLowestCostShipmentOption(results);
        } catch (UnknownFulfillmentCenterException e) {
            log.error("PackagingDao threw an exception.", e);
            throw new SustainabilityShipmentClientException(String.format("Unknown FC %s", fulfillmentCenter));
        } catch (NoPackagingFitsItemException e) {
            log.warn("Unable to find packaging option that will fit item," +
                " returning a ShipmentOption with null packaging.", e);
            return ShipmentOption.builder()
                .withFulfillmentCenter(fulfillmentCenter)
                .withItem(item)
                .withPackaging(null)
                .build();
        }
    }

    private ShipmentOption getLowestCostShipmentOption(List<ShipmentOption> results) {
        List<ShipmentCost> shipmentCosts = applyCostStrategy(results);
        Collections.sort(shipmentCosts);
        return shipmentCosts.get(0).getShipmentOption();
    }

    private List<ShipmentCost> applyCostStrategy(List<ShipmentOption> results) {
        List<ShipmentCost> shipmentCosts = new ArrayList<>();
        for (ShipmentOption option : results) {
            shipmentCosts.add(costStrategy.getCost(option));
        }
        return shipmentCosts;
    }
}
