package com.amazon.ata.service;

import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class ShipmentServiceTest {
    @Mock
    private PackagingDAO packagingDAO;
    @Mock
    private MonetaryCostStrategy monetaryCostStrategy;
    @InjectMocks
    private ShipmentService shipmentService;


    private Item smallItem;
    private Item largeItem;
    private FulfillmentCenter existentFC;
    private FulfillmentCenter nonExistentFC;
    private Box standardBox;

    @BeforeEach
    void setUp() {
        initMocks(this);


        // Initialize test data
        smallItem = Item.builder()
                .withHeight(BigDecimal.valueOf(1))
                .withWidth(BigDecimal.valueOf(1))
                .withLength(BigDecimal.valueOf(1))
                .withAsin("abcde")
                .build();

        largeItem = Item.builder()
                .withHeight(BigDecimal.valueOf(1000))
                .withWidth(BigDecimal.valueOf(1000))
                .withLength(BigDecimal.valueOf(1000))
                .withAsin("12345")
                .build();
        existentFC = new FulfillmentCenter("ABE2");
        nonExistentFC = new FulfillmentCenter("NonExistentFC");

        standardBox = new Box(
                Material.CORRUGATE,
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(20),
                BigDecimal.valueOf(20)
        );
    }

    @Test
    void findBestShipmentOption_existentFCAndItemCanFit_returnsShipmentOption() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        ShipmentOption expectedOption = ShipmentOption.builder()
                .withItem(smallItem)
                .withPackaging(standardBox)
                .withFulfillmentCenter(existentFC)
                .build();

        when(packagingDAO.findShipmentOptions(smallItem, existentFC))
                .thenReturn(Arrays.asList(expectedOption));

        ShipmentCost shipmentCost = new ShipmentCost(expectedOption, BigDecimal.valueOf(10.0));
        when(monetaryCostStrategy.getCost(any(ShipmentOption.class)))
                .thenReturn(shipmentCost);

        // WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(smallItem, existentFC);

        // THEN
        assertNotNull(shipmentOption);
        assertNotNull(shipmentOption.getPackaging(), "Packaging should not be null for fitting item");
        assertEquals(existentFC, shipmentOption.getFulfillmentCenter());
        assertEquals(smallItem, shipmentOption.getItem());
    }


    @Test
    void findBestShipmentOption_existentFCAndItemCannotFit_returnsShipmentOptionWithNullPackaging() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(largeItem, existentFC))
                .thenThrow(new NoPackagingFitsItemException(
                        String.format("No packaging at %s fits %s!", existentFC.getFcCode(), largeItem)));

        // WHEN
        ShipmentOption shipmentOption = shipmentService.findShipmentOption(largeItem, existentFC);

        // THEN
        assertNotNull(shipmentOption, "ShipmentOption should not be null");
        assertNull(shipmentOption.getPackaging(), "Packaging should be null for non-fitting item");
        assertEquals(existentFC, shipmentOption.getFulfillmentCenter());
        assertEquals(largeItem, shipmentOption.getItem());
    }

    @Test
    void findBestShipmentOption_nonExistentFCAndItemCanFit_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        String expectedMessage = String.format("Unknown FC: %s!", nonExistentFC.getFcCode());
        when(packagingDAO.findShipmentOptions(smallItem, nonExistentFC))
                .thenThrow(new UnknownFulfillmentCenterException(expectedMessage));

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        });

        // Check if the cause is UnknownFulfillmentCenterException and has the right message
        assertTrue(exception.getCause() instanceof UnknownFulfillmentCenterException);
        assertTrue(exception.getCause().getMessage().contains(expectedMessage));
    }


    @Test
    void findBestShipmentOption_nonExistentFCAndItemCannotFit_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        String expectedMessage = String.format("Unknown FC: %s!", nonExistentFC.getFcCode());
        when(packagingDAO.findShipmentOptions(largeItem, nonExistentFC))
                .thenThrow(new UnknownFulfillmentCenterException(expectedMessage));

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        });

        // Check if the cause is UnknownFulfillmentCenterException and has the right message
        assertTrue(exception.getCause() instanceof UnknownFulfillmentCenterException);
        assertTrue(exception.getCause().getMessage().contains(expectedMessage));
    }

    @Test
    void findBestShipmentOption_nonExistentFC_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(smallItem, nonExistentFC))
                .thenThrow(new UnknownFulfillmentCenterException(
                        String.format("Unknown FC: %s!", nonExistentFC.getFcCode())));

        // WHEN & THEN
        Exception exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(smallItem, nonExistentFC);
        });
        assertTrue(exception.getMessage().contains("Unknown FC: NonExistentFC!"),
                "Exception message should match PackagingDAO's message");
    }


    @Test
    void findBestShipmentOption_nonExistentFCAndLargeItem_throwsRuntimeException() throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {
        // GIVEN
        when(packagingDAO.findShipmentOptions(largeItem, nonExistentFC))
                .thenThrow(new UnknownFulfillmentCenterException(
                        String.format("Unknown FC: %s!", nonExistentFC.getFcCode())));

        // WHEN & THEN
        Exception exception = assertThrows(RuntimeException.class, () -> {
            shipmentService.findShipmentOption(largeItem, nonExistentFC);
        });
        assertTrue(exception.getMessage().contains("Unknown FC: NonExistentFC!"),
                "Exception message should match PackagingDAO's message");
    }
}