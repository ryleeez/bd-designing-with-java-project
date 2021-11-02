package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.one;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.SustainabilityShipmentIntegrationTestBase;

import com.amazonaws.services.shipmentservice.model.PrepareShipmentRequest;
import com.amazonaws.services.shipmentservice.model.PrepareShipmentResult;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MasteryTaskOneTests extends SustainabilityShipmentIntegrationTestBase {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void masteryTask1_prepareShipment_returnValidShipmentOption() {
        PrepareShipmentRequest request = new PrepareShipmentRequest()
            .withFcCode("IND1")
            .withItemAsin("3141592")
            .withItemDescription("DEFAULT")
            .withItemLength("5.11")
            .withItemWidth("5.11")
            .withItemHeight("5.11");
        System.out.println("Prepare shipment is:" + request);

        PrepareShipmentResult result = shipmentServiceClient.prepareShipment(request);

        String attributesJSON = result.getAttributes();

        Assert.assertNotNull("Mysterious error message...", attributesJSON);
    }
}
