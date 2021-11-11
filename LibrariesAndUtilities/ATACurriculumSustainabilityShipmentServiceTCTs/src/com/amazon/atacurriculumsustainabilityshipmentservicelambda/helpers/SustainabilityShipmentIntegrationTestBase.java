package com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers;

import com.amazon.ata.integration.test.LambdaIntegrationTestBase;

import com.amazonaws.services.shipmentservice.ShipmentService;
import com.amazonaws.services.shipmentservice.ShipmentServiceClientBuilder;


public class SustainabilityShipmentIntegrationTestBase extends LambdaIntegrationTestBase {
    protected ShipmentService shipmentServiceClient;

    /**
     * Configures the shipmentServiceClient to be used in the test class.
     * @throws Exception when the client cannot be created
     */
    public void setup() throws Exception {
        this.shipmentServiceClient = getServiceClient(
            ShipmentServiceClientBuilder.standard(),
            "ATACurriculumSustainabilityShipmentService");
    }
}
