package com.amazon.atacurriculumsustainabilityshipmentservicelambda.service;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.SustainabilityShipmentClientException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao.PackagingDAO;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.NoPackagingFitsItemException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions.UnknownFulfillmentCenterException;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Box;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ShipmentServiceTest {

    private Item item = Item.builder()
        .withHeight(BigDecimal.valueOf(1))
        .withWidth(BigDecimal.valueOf(1))
        .withLength(BigDecimal.valueOf(1))
        .withAsin("abcde")
        .build();

    private Box highCostPackaging = Box.builder()
        .withHeight(BigDecimal.TEN)
        .withLength(BigDecimal.TEN)
        .withWidth(BigDecimal.TEN)
        .build();
    private Box lowCostPackaging = Box.builder()
        .withHeight(BigDecimal.ONE)
        .withLength(BigDecimal.ONE)
        .withWidth(BigDecimal.ONE)
        .build();

    private FulfillmentCenter fc = new FulfillmentCenter("ABE2");
    private ShipmentOption highCostShipmentOption = ShipmentOption.builder()
        .withItem(item)
        .withPackaging(highCostPackaging)
        .withFulfillmentCenter(fc)
        .build();
    private ShipmentOption lowCostShipmentOption = ShipmentOption.builder()
        .withItem(item)
        .withPackaging(lowCostPackaging)
        .withFulfillmentCenter(fc)
        .build();


    @Mock
    private PackagingDAO packagingDAO;

    @Mock
    private CostStrategy costStrategy;

    @InjectMocks
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        when(costStrategy.getCost(highCostShipmentOption)).thenReturn(
            new ShipmentCost(highCostShipmentOption, BigDecimal.TEN)
        );

        when(costStrategy.getCost(lowCostShipmentOption)).thenReturn(
            new ShipmentCost(lowCostShipmentOption, BigDecimal.ONE)
        );
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsLowestCostShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(item, fc)).thenReturn(
            Arrays.asList(highCostShipmentOption, lowCostShipmentOption)
        );
        // WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(item, fc);

        // THEN
        assertEquals(lowCostShipmentOption, shipmentOption);
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOptionWithNullPackaging() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(any(), any())).thenThrow(new NoPackagingFitsItemException());

        // WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(item, fc);

        // THEN
        assertNotNull(shipmentOption);
        assertEquals(shipmentOption.getFulfillmentCenter(), fc);
        assertEquals(shipmentOption.getItem(), item);
        assertNull(shipmentOption.getPackaging());
    }

    @Test
    void findBestShipmentOption_nonExistentFC_throwsSustainabilityShipmentClientException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(any(), any())).thenThrow(new UnknownFulfillmentCenterException());

        // WHEN + THEN
        assertThrows(SustainabilityShipmentClientException.class,
            () -> shipmentService.findShipmentOption(item, fc));
    }
}
