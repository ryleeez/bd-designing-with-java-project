package com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Box;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Packaging;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.PolyBag;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeightedCostStrategyTest {
    private static final Packaging BOX_10x10x20 =
        new Box(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));
    private static final Packaging POLYBAG_200 =
        new PolyBag(BigDecimal.valueOf(200));

    private CostStrategy monetaryCostStrategy = new MonetaryCostStrategy();
    private CostStrategy carbonCostStrategy = new CarbonCostStrategy();

    private CostStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = WeightedCostStrategy.builder()
            .addStrategyWithWeight(monetaryCostStrategy, new BigDecimal("0.8"))
            .addStrategyWithWeight(carbonCostStrategy, new BigDecimal("0.2"))
            .build();
    }

    @Test
    void getCost_weightedCostStrategyWithConstructor_usesDefaultWeights() {
        // GIVEN a weighted cost strategy created with a constructor
        CostStrategy defaultWeightedCostStrategy = new WeightedCostStrategy(new MonetaryCostStrategy(),
            new CarbonCostStrategy());

        ShipmentOption polyBag = ShipmentOption.builder()
            .withPackaging(POLYBAG_200)
            .build();

        ShipmentOption box = ShipmentOption.builder()
            .withPackaging(BOX_10x10x20)
            .build();

        // WHEN we call getCost
        BigDecimal polyBagCost = defaultWeightedCostStrategy.getCost(polyBag).getCost();
        BigDecimal boxCost = defaultWeightedCostStrategy.getCost(box).getCost();

        // THEN it should use the default weights (monetary - 0.8, carbon - 0.2)
        assertEquals(new BigDecimal("2.16560"), polyBagCost,
            "Expected a weighted cost of 2.16560 for polybag " + POLYBAG_200);
        assertEquals(new BigDecimal("7.7440"), boxCost,
            "Expected a weighted cost of 7.7440 for box " + BOX_10x10x20);
    }

    @Test
    void getCost_forB2kBox_givesExpectedResult() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
            .withPackaging(BOX_10x10x20)
            .build();

        // WHEN
        BigDecimal cost = strategy.getCost(option).getCost();

        // THEN
        assertEquals(new BigDecimal("7.7440"), cost, "Expected a weighted cost of 7.7440");
    }

    @Test
    void getCost_forp20PolyBag_givesExpectedResult() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
            .withPackaging(POLYBAG_200)
            .build();

        // WHEN
        BigDecimal cost = strategy.getCost(option).getCost();

        // THEN
        assertEquals(new BigDecimal("2.16560"), cost, "Expected a weighted cost of 5.80880");
    }
}
