package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.six;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.PrepareShipmentResultProcessor;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.SustainabilityShipmentIntegrationTestBase;

import com.amazonaws.services.shipmentservice.model.PrepareShipmentRequest;
import com.amazonaws.services.shipmentservice.model.PrepareShipmentResult;
import com.amazonaws.services.shipmentservice.model.SustainabilityShipmentClientException;
import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class MasteryTaskSixTests extends SustainabilityShipmentIntegrationTestBase {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void masteryTask6_prepareShipment_atInvalidFc_throwsException() {
        // GIVEN - request to prepare shipment at non-existent FC
        PrepareShipmentRequest request = new PrepareShipmentRequest()
            .withFcCode("NoSuchFc")
            .withItemAsin("123456789")
            .withItemDescription("Something modest")
            .withItemLength("5")
            .withItemWidth("5")
            .withItemHeight("5");
        System.out.println("Prepare shipment is:" + request);

        // WHEN + THEN - IllegalParameterException
        assertThrows(SustainabilityShipmentClientException.class, () -> shipmentServiceClient.prepareShipment(request));
    }

    @Test
    public void masteryTask6_prepareShipment_withTooLargeItem_returnsNull() {
        // GIVEN - request for very large item
        PrepareShipmentRequest request = new PrepareShipmentRequest()
            .withFcCode("IAD2")
            .withItemAsin("123456789")
            .withItemDescription("Something large")
            .withItemLength("100")
            .withItemWidth("100")
            .withItemHeight("100");
        System.out.println("Prepare shipment is:" + request);

        // WHEN
        PrepareShipmentResult result = shipmentServiceClient.prepareShipment(request);

        // THEN - packaging is 'null'
        JsonNode packagingNode = new PrepareShipmentResultProcessor(result).getPackagingNode();
        assertEquals(
            packagingNode.asText(),
            "null",
            "Expected 'null' packaging in response to prepare shipment for a very large item. " +
                "Packaging was " + packagingNode.asText()
        );
    }
}
