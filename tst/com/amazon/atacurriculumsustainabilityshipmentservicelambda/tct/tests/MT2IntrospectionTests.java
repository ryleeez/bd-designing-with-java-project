package com.amazon.atacurriculumsustainabilityshipmentservicelambda.tct.tests;

import com.amazon.ata.test.helper.AtaTestHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MT2IntrospectionTests {
    private static final String LOCAL_LOG_TEST_TAG = "MT02_LOCAL";
    private static final String CLOUDWATCH_LOG_TEST_TAG = "MT02_CLOUDWATCH";

    @Tag(LOCAL_LOG_TEST_TAG)
    @Test
    public void mt2_getLocalLogOutputFile_nonEmptyFileExists() {
        // GIVEN
        String responseOneFileName = "mastery-task2-local.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain local log contents",
            responseOneFileName));
    }

    @Tag(LOCAL_LOG_TEST_TAG)
    @Test
    public void mt2_getLocalLogOutputFile_containsExpectedValues() {
        // GIVEN
        String responseOneFileName = "mastery-task2-local.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertTrue(content.contains("com.amazon.atacurriculumsustainabilityshipmentservicelambda.service" +
            ".ShipmentService"), String.format("Expected file: %s to contain a log line from the correct class",
            responseOneFileName));
        assertTrue(content.contains("0132350882"),
            String.format("Expected file: %s to contain a log line with the item ASIN", responseOneFileName));
        assertTrue(content.contains("Clean Code"),
            String.format("Expected file: %s to contain a log line with the item description", responseOneFileName));
        assertTrue(content.contains("7.0"),
            String.format("Expected file: %s to contain a log line with all item dimensions", responseOneFileName));
        assertTrue(content.contains("1.2"),
            String.format("Expected file: %s to contain a log line with all item dimensions", responseOneFileName));
        assertTrue(content.contains("9.2"),
            String.format("Expected file: %s to contain a log line with all item dimensions", responseOneFileName));
        assertTrue(content.contains("IND1"),
            String.format("Expected file: %s to contain a log line with the fulfillment center code",
            responseOneFileName));
    }

    @Tag(CLOUDWATCH_LOG_TEST_TAG)
    @Test
    public void mt2_getCloudWatchLogOutputFile_nonEmptyFileExists() {
        // GIVEN
        String responseOneFileName = "mastery-task2-cloudwatch.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain the cloudwatch log message",
            responseOneFileName));
    }

    @Tag(CLOUDWATCH_LOG_TEST_TAG)
    @Test
    public void mt2_getCloudWatchLogOutputFile_containsExpectedValues() {
        // GIVEN
        String responseOneFileName = "mastery-task2-cloudwatch.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertTrue(content.contains("com.amazon.atacurriculumsustainabilityshipmentservicelambda.service" +
            ".ShipmentService"), String.format("Expected file: %s to contain a log line from the correct class",
            responseOneFileName));
        assertTrue(content.contains("3141592"),
            String.format("Expected file: %s to contain a log line with the item ASIN", responseOneFileName));
        assertTrue(content.contains("DEFAULT"),
            String.format("Expected file: %s to contain a log line with the item name", responseOneFileName));

        int dimensionCount = StringUtils.countMatches(content, "5.11");
        assertEquals(3, dimensionCount,
            String.format("Expected file: %s to contain a log line with all item dimensions", responseOneFileName));
        assertTrue(content.contains("IND1"),
            String.format("Expected file: %s to contain a log line with the fulfillment center code",
                responseOneFileName));
    }
}
