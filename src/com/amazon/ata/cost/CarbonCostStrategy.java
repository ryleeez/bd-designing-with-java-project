package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;


import java.math.BigDecimal;

public class CarbonCostStrategy implements CostStrategy {
        private static final BigDecimal CORRUGATE_CARBON_COST = new BigDecimal("0.017");
        private static final BigDecimal LAMINATED_PLASTIC_CARBON_COST = new BigDecimal("0.012");

        @Override
        public ShipmentCost getCost(ShipmentOption shipmentOption) {
            BigDecimal carbonCost = calculateCarbonCost(shipmentOption);
            return new ShipmentCost(shipmentOption, carbonCost);
        }
    private BigDecimal calculateCarbonCost(ShipmentOption shipmentOption) {
        Material material = shipmentOption.getPackaging().getMaterial();
        BigDecimal mass = shipmentOption.getPackaging().getMass();

        BigDecimal carbonCostPerGram = getCarbonCostPerGram(material);
        return carbonCostPerGram.multiply(mass);
    }
        private BigDecimal getCarbonCostPerGram(Material material) {
            switch (material) {
                case CORRUGATE:
                    return CORRUGATE_CARBON_COST;
                case LAMINATED_PLASTIC:
                    return LAMINATED_PLASTIC_CARBON_COST;
                default:
                    throw new IllegalArgumentException("Unknown packaging material: " + material);
            }
        }
}
