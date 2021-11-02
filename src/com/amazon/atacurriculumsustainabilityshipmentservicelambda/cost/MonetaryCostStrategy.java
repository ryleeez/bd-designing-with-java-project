package com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Material;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Packaging;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * A strategy to calculate the cost of a ShipmentOption based on its dollar cost.
 */
public class MonetaryCostStrategy implements CostStrategy {

    private static final BigDecimal LABOR_COST = BigDecimal.valueOf(0.43);
    private final Map<Material, BigDecimal> materialCostPerGram;

    /**
     * Initializes a MonetaryCostStrategy.
     */
    public MonetaryCostStrategy() {
        materialCostPerGram = new HashMap<>();
        materialCostPerGram.put(Material.CORRUGATE, BigDecimal.valueOf(.005));
        materialCostPerGram.put(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(.25));
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal materialCost = this.materialCostPerGram.get(packaging.getMaterial());

        BigDecimal cost = packaging.getMass().multiply(materialCost)
            .add(LABOR_COST);

        return new ShipmentCost(shipmentOption, cost);
    }
}
