package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentRequest;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentResponse;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.service.ShipmentService;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;
import com.amazon.bones.util.DataConverter;
import com.amazon.bones.util.DataConverterImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class PrepareShipmentActivityTest {

    private PrepareShipmentRequest request = PrepareShipmentRequest.builder()
        .withFcCode("fcCode")
        .withItemAsin("itemAsin")
        .withItemDescription("description")
        .withItemLength("10.0")
        .withItemWidth("10.0")
        .withItemHeight("10.0")
        .build();

    private DataConverter dataConverter = new DataConverterImpl();

    @Mock
    private ShipmentService shipmentService;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    public void handleRequest_noAvailableShipmentOption_returnsNull() throws Exception {
        // GIVEN
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService, dataConverter);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class))).thenReturn(null);

        // WHEN
        PrepareShipmentResponse response = activity.handleRequest(request);

        // THEN
        assertNull(response.getAttributes());
    }

    @Test
    public void handleRequest_availableShipmentOption_returnsNonEmptyResponse() throws Exception {
        // GIVEN
        PrepareShipmentActivity activity = new PrepareShipmentActivity(shipmentService, dataConverter);
        when(shipmentService.findShipmentOption(any(Item.class), any(FulfillmentCenter.class)))
            .thenReturn(ShipmentOption.builder().build());

        // WHEN
        PrepareShipmentResponse response = activity.handleRequest(request);

        // THEN
        assertNotNull(response.getAttributes());
    }
}
