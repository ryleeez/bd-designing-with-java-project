package com.amazon.atacurriculumsustainabilityshipmentservicelambda.tct;

import com.amazon.ata.test.helper.AtaTestHelper;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("PPT01")
public class PPT1IntrospectionTests {

    @Test
    public void ppt1_getResponseFile1_nonEmptyFileExists() {
        // GIVEN
        String responseOneFileName = "ppt1-response1.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain response contents",
            responseOneFileName));
    }

    @Test
    public void ppt1_getResponseFile1_contentCorrect() {
        // GIVEN
        String responseOneFileName = "ppt1-response1.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseOneFileName);

        // THEN
        assertTrue(contentContains(content, "Status Code: 200"),
            "Incorrect status code.");
        assertTrue(contentContains(content, "\"asin\" : \"12345\""),
            "Incorrect item in response.");
        assertTrue(contentContains(content, "\"material\" : \"CORRUGATE\""),
            "Incorrect packaging in response.");
        assertTrue(validateLength(content, 226), "Incorrect contents of " +
            "file. Length not as expected");
    }

    @Test
    public void ppt1_getResponseFile2_nonEmptyFileExists() {
        // GIVEN
        String responseTwoFileName = "ppt1-response2.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseTwoFileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain response contents",
            responseTwoFileName));
    }

    @Test
    public void ppt1_getResponseFile2_contentCorrect() {
        // GIVEN
        String responseTwoFileName = "ppt1-response2.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseTwoFileName);

        // THEN
        assertTrue(contentContains(content, "Status Code: 200"),
            "Incorrect status code.");
        assertTrue(contentContains(content, "Body: null"),
            "Expected the response body to be null.");
        assertTrue(validateLength(content, 23), "Incorrect contents of file. " +
            "Length not as expected");
    }

    @Test
    public void ppt1_getResponseFile3_nonEmptyFileExists() {
        // GIVEN
        String responseThreeFileName = "ppt1-response3.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseThreeFileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain response contents",
            responseThreeFileName));
    }

    @Test
    public void ppt1_getResponseFile3_contentCorrect() {
        // GIVEN
        String responseThreeFileName = "ppt1-response3.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(responseThreeFileName);

        // THEN
        assertTrue(contentContains(content, "Status Code: 400"),
            "Incorrect status code.");
        assertTrue(contentContains(content, "Error Type:  SustainabilityShipmentClientException"),
            "Expected the response body to contain an error type.");
        assertTrue(contentContains(content, "Message:  1 validation error detected:  " +
            "Value '' at 'fcCode' failed to satisfy constraint"),
            "Expected the response body to contain an error message.");
        assertTrue(validateLength(content, 178), "Incorrect contents of file. " +
            "Length not as expected");
    }

    private boolean contentContains(String content, String searchStr) {
        String noWhitespaceContent = content.replaceAll("\\s+", "");
        String noWhitespaceSearchStr = searchStr.replaceAll("\\s+", "");
        return noWhitespaceContent.contains(noWhitespaceSearchStr);
    }

    private boolean validateLength(String content, int lengthWithoutWhitespace) {
        // Expected responses are all whole numbers and different versions of the service return whole numbers and some
        // return decimals. Strip out any decimals to check across versions.
        String removeDecimals = content.replaceAll("\\.0+", "");
        String noWhitespaceContent = removeDecimals.replaceAll("\\s+", "");
        return noWhitespaceContent.length() == lengthWithoutWhitespace;
    }
}
