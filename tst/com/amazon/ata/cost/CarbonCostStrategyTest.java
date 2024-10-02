package com.amazon.ata.cost;

import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CarbonCostStrategyTest {

    private CarbonCostStrategy carbonCostStrategy;

    @Mock
    private ShipmentOption mockShipmentOption;

    @Mock
    private Packaging mockPackaging;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        carbonCostStrategy = new CarbonCostStrategy();
        when(mockShipmentOption.getPackaging()).thenReturn(mockPackaging);
    }

    @Test
    public void getCost_corrugateMaterial_returnsCarbonCost() {
        // GIVEN
        when(mockPackaging.getMaterial()).thenReturn(Material.CORRUGATE);
        when(mockPackaging.getMass()).thenReturn(BigDecimal.valueOf(1000.0));

        // WHEN
        ShipmentCost result = carbonCostStrategy.getCost(mockShipmentOption);

        // THEN
        assertEquals(new BigDecimal("17.0000"), result.getCost());
        assertEquals(mockShipmentOption, result.getShipmentOption());
    }

    @Test
    public void getCost_laminatedPlasticMaterial_returnsCarbonCost() {
        // GIVEN
        when(mockPackaging.getMaterial()).thenReturn(Material.LAMINATED_PLASTIC);
        when(mockPackaging.getMass()).thenReturn(BigDecimal.valueOf(27.0));

        // WHEN
        ShipmentCost result = carbonCostStrategy.getCost(mockShipmentOption);

        // THEN
        assertEquals(new BigDecimal("0.3240"), result.getCost());
        assertEquals(mockShipmentOption, result.getShipmentOption());
    }
}
