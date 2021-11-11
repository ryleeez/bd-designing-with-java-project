package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.four.polybag;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.PrepareShipmentResultProcessor;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.PrepareShipmentRequest;
import com.amazonaws.services.shipmentservice.model.PrepareShipmentResult;
import com.amazonaws.services.shipmentservice.model.TctResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class MasteryTaskFourTests extends TctIntrospectionTest {
    private static final String LAMINATED_PLASTIC = "LAMINATED_PLASTIC";

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    public String getTestSuiteId() {
        return "MT04";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask4_runIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }

    @Test
    public void masteryTask4_prepareShipment_withVeryLargeItem_returnsLargerPolyBag() {
        // GIVEN - shipment request for 20x20x24 item at IAD2
        PrepareShipmentRequest request = new PrepareShipmentRequest()
            .withFcCode("IAD2")
            .withItemAsin("123456789")
            .withItemDescription("Kind of a biggish item that should fit into a large PolyBag")
            .withItemLength("20.0")
            .withItemWidth("20.0")
            .withItemHeight("24.0");

        // WHEN
        PrepareShipmentResult result = shipmentServiceClient.prepareShipment(request);

        // THEN - 10000cc polybag
        Pair<String, String> expectedMaterialAndVolume = new ImmutablePair<>(LAMINATED_PLASTIC, "10000");
        assertMaterialAndVolumeInResponse(result, Sets.newHashSet(expectedMaterialAndVolume));
    }

    @Test
    public void masteryTask4_prepareShipment_withModeratelyLargeItem_returnsPolyBag() {
        // GIVEN - shipment request for 5x9x25 item at IAD2
        PrepareShipmentRequest request = new PrepareShipmentRequest()
            .withFcCode("IAD2")
            .withItemAsin("567890123")
            .withItemDescription("An item that should fit into a smaller PolyBag")
            .withItemLength("5.0")
            .withItemWidth("9.0")
            .withItemHeight("25.0");

        // WHEN
        PrepareShipmentResult result = shipmentServiceClient.prepareShipment(request);

        // THEN - 2000cc or 10000cc polybag
        Pair<String, String> possibleMaterialAndVolume1 = new ImmutablePair<>(LAMINATED_PLASTIC, "2000");
        Pair<String, String> possibleMaterialAndVolume2 = new ImmutablePair<>(LAMINATED_PLASTIC, "10000");
        assertMaterialAndVolumeInResponse(result,
                Sets.newHashSet(possibleMaterialAndVolume1, possibleMaterialAndVolume2));
    }

    private void assertMaterialAndVolumeInResponse(
            PrepareShipmentResult result, Set<Pair<String, String>> expectedMaterialAndVolumePairs) {
        JsonNode packagingJson = new PrepareShipmentResultProcessor(result).getPackagingNode();

        JsonNode volumeNode = packagingJson.get("volume");
        JsonNode materialNode = packagingJson.get("material");

        if (null == volumeNode) {
            fail("Did not find 'volume' field in packaging in prepare shipment response. Packaging JSON was:\n" +
                packagingJson.asText());
        }

        if (null == materialNode) {
            fail("Did not find 'material' field in packaging in prepare shipment response. Packaging JSON was:\n" +
                packagingJson.asText());
        }

        String volume = volumeNode.asText().trim();
        String material = materialNode.asText().trim();

        Pair<String, String> actualMaterialAndVolume = new ImmutablePair<>(material, volume);
        assertTrue(expectedMaterialAndVolumePairs.contains(actualMaterialAndVolume),
                String.format("Expected response to include Packaging (with material and volume equal to any of %s), " +
                        "but instead had %s, %s.", expectedMaterialAndVolumePairs, material, volume));

    }
}
