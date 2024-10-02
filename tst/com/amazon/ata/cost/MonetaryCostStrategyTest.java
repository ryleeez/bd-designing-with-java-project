package com.amazon.ata.cost;

import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MonetaryCostStrategyTest {

    private MonetaryCostStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new MonetaryCostStrategy();
    }

    @Test
    void getCost_corrugateMaterial_returnsCorrectCost() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN);
        ShipmentOption option = ShipmentOption.builder()
                .withPackaging(box)
                .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(option);

        // THEN
        BigDecimal expectedCost = BigDecimal.valueOf(3.43);
        assertTrue(expectedCost.compareTo(shipmentCost.getCost()) == 0,
                String.format("Expected shipment cost to be %s, but was %s", expectedCost, shipmentCost.getCost()));
    }
    @Test
    public void getCost_polybagShipmentOption_calculatesCorrectCost() {
        // GIVEN
        PolyBag polyBag = new PolyBag(Material.LAMINATED_PLASTIC, BigDecimal.valueOf(1000));
        ShipmentOption shipmentOption = ShipmentOption.builder()
                .withPackaging(polyBag)
                .build();

        // WHEN
        ShipmentCost shipmentCost = strategy.getCost(shipmentOption);

        // THEN
        BigDecimal expectedCost = BigDecimal.valueOf(19 * 0.25 + 0.43);
        assertTrue(expectedCost.compareTo(shipmentCost.getCost()) == 0,
                String.format("Expected shipment cost to be %s, but was %s", expectedCost, shipmentCost.getCost()));
    }
}
