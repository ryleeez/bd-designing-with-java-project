package com.amazon.atacurriculumsustainabilityshipmentservicelambda.tct;

import com.amazon.ata.test.helper.AtaTestHelper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.amazon.ata.test.assertions.AtaAssertions.assertMatchesSingleLine;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsClass;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsEnum;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramContainsInterface;
import static com.amazon.ata.test.assertions.PlantUmlClassDiagramAssertions.assertClassDiagramIncludesContainsRelationship;
import static com.amazon.ata.test.assertions.PlantUmlSequenceDiagramAssertions.assertSequenceDiagramContains;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Tag("PPT03")
public class PPT3IntrospectionTests {
    private static final String CLASS_DIAGRAM_PATH = "sustainable_packaging_CD.puml";
    private static final String SEQUENCE_DIAGRAM_PATH = "sustainable_packaging_SD.puml";

    @Test
    void ppt3_getClassDiagram_nonEmptyFileExists() {
        // GIVEN - diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN
        assertFalse(content.trim().isEmpty(),
                    String.format("Expected file: %s to contain class diagram but was empty", CLASS_DIAGRAM_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "ShipmentService", "PackagingDAO", "PackagingDatastore", "Item", "FulfillmentCenter", "ShipmentOption",
        "Packaging", "FcPackagingOption", "ShipmentCost", "MonetaryCostStrategy"})
    void ppt3_getClassDiagram_includesExpectedTypes(String expectedClass) {
        // GIVEN - diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - contains each class
        assertClassDiagramContainsClass(content, expectedClass);
    }

    @Test
    void ppt3_getClassDiagram_includesCostStrategyInterface() {
        // GIVEN -- diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - contains interface declaration for CostStrategy
        assertClassDiagramContainsInterface(content, "CostStrategy");
    }

    @Test
    void ppt3_getClassDiagram_includesMaterialType() {
        // GIVEN -- diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - contains enum declaration for Material
        assertClassDiagramContainsEnum(content, "Material");
    }

    @Test
    void ppt3_getClassDiagram_includesExpectedRelationships() {
        // GIVEN - diagram path

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(CLASS_DIAGRAM_PATH);

        // THEN - diagram includes these has-a relationships
        assertClassDiagramIncludesContainsRelationship(content, "ShipmentService", "PackagingDAO");
        assertClassDiagramIncludesContainsRelationship(content, "ShipmentService", "CostStrategy");
        assertClassDiagramIncludesContainsRelationship(content, "FcPackagingOption", "Packaging");
        assertClassDiagramIncludesContainsRelationship(
            content, "FcPackagingOption", "FulfillmentCenter");
        assertClassDiagramIncludesContainsRelationship(content, "ShipmentOption", "Item");
        assertClassDiagramIncludesContainsRelationship(content, "ShipmentOption", "Packaging");
        assertClassDiagramIncludesContainsRelationship(
            content, "ShipmentOption", "FulfillmentCenter");
        assertClassDiagramIncludesContainsRelationship(content, "Packaging", "Material");
    }

    @Test
    void ppt3_getSequenceDiagram_nonEmptyFileExists() {
        // GIVEN - diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(SEQUENCE_DIAGRAM_PATH);

        // THEN
        assertFalse(
            content.trim().isEmpty(),
            String.format("Expected file: %s to contain sequence diagram but was empty", SEQUENCE_DIAGRAM_PATH));
    }

    @ParameterizedTest
    @ValueSource(strings = {"ShipmentService", "PackagingDAO", "Packaging", "CostStrategy"})
    void ppt3_getSequenceDiagram_includesExpectedTypes(String expectedType) {
        // GIVEN - diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(SEQUENCE_DIAGRAM_PATH);

        // THEN - diagram includes expected types
        assertSequenceDiagramContains(content, expectedType);
    }

    @ParameterizedTest
    @ValueSource(strings = {"findShipmentOption[^s]", "findShipmentOptions", "canFitItem", "getCost", "sort"})
    void ppt3_getSequenceDiagram_includesExpectedMethodCalls(String expectedMethod) {
        // GIVEN - diagram path
        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(SEQUENCE_DIAGRAM_PATH);

        // THEN - diagram includes expected methods
        assertMatchesSingleLine(
            content,
            expectedMethod,
            String.format("Expected to see %s method call in sequence diagram", expectedMethod));
    }
}
