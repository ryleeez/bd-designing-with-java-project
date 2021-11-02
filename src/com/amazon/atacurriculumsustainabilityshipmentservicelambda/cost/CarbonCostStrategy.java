package com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Material;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Packaging;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CarbonCostStrategy implements CostStrategy {
    private final Map<Material, BigDecimal> materialSustainabilityIndex = new HashMap<>();

    /**
     * Initializes a CarbonCostStrategy.
     */
    public CarbonCostStrategy() {
        materialSustainabilityIndex.put(Material.CORRUGATE, BigDecimal.valueOf(0.017));
        materialSustainabilityIndex.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(0.012));
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal sustainabilityIndex = this.materialSustainabilityIndex.get(packaging.getMaterial());
        BigDecimal cost = packaging.getMass().multiply(sustainabilityIndex);

        return new ShipmentCost(shipmentOption, cost);
    }
}
