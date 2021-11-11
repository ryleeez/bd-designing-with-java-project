package com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers;

import com.amazonaws.services.shipmentservice.model.PrepareShipmentResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;

import static org.testng.Assert.fail;

/**
 * Extracts relevant data from {@code PrepareShipmentResult} objects, generating
 * appropriate assertion failures with meaningful failure messages.
 */
public class PrepareShipmentResultProcessor {
    private static final String COST_FIELD_NAME = "cost";
    private static final String COST_STRATEGY_NAME_FIELD_NAME = "costStrategyName";
    private static final String PACKAGING_FIELD_NAME = "packaging";

    private PrepareShipmentResult prepareShipmentResult;

    /**
     * Creates a new processor with the given result.
     *
     * @param prepareShipmentResult The {@code PrepareShipmentResult} to process
     */
    public PrepareShipmentResultProcessor(PrepareShipmentResult prepareShipmentResult) {
        this.prepareShipmentResult = prepareShipmentResult;
    }

    /**
     * Returns the root JSON node if exists, will {@code fail()} if it couldn't be found.
     *
     * @return The JSON root (as a {@code JsonNode}
     */
    public JsonNode getJsonRoot() {
        if (null == prepareShipmentResult.getAttributes()) {
            fail("Response body was null and didn't contain any data");
        }

        JsonNode rootJson = null;
        try {
            rootJson = (new ObjectMapper()).readTree(prepareShipmentResult.getAttributes());
        } catch (IOException e) {
            fail("Could not parse JSON in response:\n" + prepareShipmentResult.getAttributes());
        }

        return rootJson;
    }

    /**
     * Extracts the cost strategy name from the result. Will {@code fail()} if not found.
     *
     * @return The cost strategy name from the result
     */
    public String getCostStrategyName() {
        JsonNode costStrategyNode = getJsonRoot().get(COST_STRATEGY_NAME_FIELD_NAME);
        if (null == costStrategyNode) {
            fail(String.format(
                "Expected service response JSON to include a '%s' field. JSON was:%n%s",
                COST_STRATEGY_NAME_FIELD_NAME,
                getJsonRoot().asText())
            );
        }

        return costStrategyNode.asText();
    }

    /**
     * Extracts the cost from the result. Will {@code fail()} if not found.
     *
     * @return The cost from the result, represented as {@code BigDecimal}.
     */
    public BigDecimal getCost() {
        JsonNode costNode = getJsonRoot().get(COST_FIELD_NAME);
        if (null == costNode) {
            fail(String.format(
                "Expected service response JSON to include a '%s' field. JSON was:%n%s",
                COST_FIELD_NAME,
                getJsonRoot().asText())
            );
        }

        return new BigDecimal(costNode.asText());
    }

    /**
     * Returns the node in the response JSON corresponding to the chosen shipment packaging.
     * Will {@code fail()} if not found.
     *
     * @return The JSON node corresponding to the shipment packaging
     */
    public JsonNode getPackagingNode() {
        JsonNode packagingJson = getJsonRoot().get(PACKAGING_FIELD_NAME);

        // try to diagnose possible errors in representation?
        if (null == packagingJson) {
            JsonNode boxJson = getJsonRoot().get("box");
            if (null != boxJson) {
                fail(String.format(
                    "Expected service response JSON to include a '%s' node, but found 'box' instead",
                    PACKAGING_FIELD_NAME)
                );
            }

            JsonNode polyBagJson = getJsonRoot().get("polybag");
            if (null != polyBagJson) {
                fail(String.format(
                    "Expected service response JSON to include a '%s' node, but found 'polybag' instead",
                    PACKAGING_FIELD_NAME)
                );
            }

            fail(String.format(
                "Could not find '%s' node in prepare shipment response. JSON was:%n%s",
                PACKAGING_FIELD_NAME,
                getJsonRoot().asText())
            );
        }

        return packagingJson;
    }
}
