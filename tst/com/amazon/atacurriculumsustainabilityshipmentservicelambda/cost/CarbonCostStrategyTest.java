package com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Box;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Packaging;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.PolyBag;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarbonCostStrategyTest {

    private static final Packaging BOX_10x10x20 =
        new Box(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(20));

    private static final Packaging POLYBAG_200 =
        new PolyBag(BigDecimal.valueOf(200));

    private CarbonCostStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CarbonCostStrategy();
    }

    @Test
    void getCost_corrugateMaterial_returnsCorrectCost() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
            .withPackaging(BOX_10x10x20)
            .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        assertTrue(BigDecimal.valueOf(17.000).compareTo(shipmentCost.getCost()) == 0,
            "Incorrect monetary cost calculation for a box with dimensions 10x10x20.");
    }

    @Test
    void getCost_laminatedPlasticMaterial_returnsCorrectCost() {
        // GIVEN
        ShipmentOption option = ShipmentOption.builder()
            .withPackaging(POLYBAG_200)
            .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        assertTrue(BigDecimal.valueOf(0.1080).compareTo(shipmentCost.getCost()) == 0,
            "Incorrect monetary cost calculation for a polybag with volume 200");
    }
}
