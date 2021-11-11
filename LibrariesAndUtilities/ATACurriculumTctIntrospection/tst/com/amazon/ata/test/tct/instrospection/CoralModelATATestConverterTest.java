package com.amazon.ata.test.tct.instrospection;

import com.amazon.ata.test.tct.introspection.types.TctId;
import com.amazon.ata.test.tct.introspection.types.TctResult;
import com.amazon.ata.test.tct.introspection.types.TctSuiteReport;
import com.amazon.ata.test.types.ATATestId;
import com.amazon.ata.test.types.ATATestResult;
import com.amazon.ata.test.types.ATATestSuiteId;
import com.amazon.ata.test.types.ATATestSuiteReport;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoralModelATATestConverterTest {
    private static final String DISPLAY_NAME = "given_when_then";
    private static final String PARENT_NAME = "ClassTest";
    private static final String ERROR_MESSAGE = "Stack trace...";
    private static final String SUITE_ID = "PPT01";

    @Test
    public void toCoral_givenAtaTestId_returnsTctId() {
        // GIVEN
        ATATestId ataTestId = ATATestId.builder()
                .withDisplayName(DISPLAY_NAME)
                .withParentDisplayName(PARENT_NAME)
                .build();

        // WHEN
        TctId tctId = CoralModelATATestConverter.toCoral(ataTestId);

        // THEN
        assertEquals(DISPLAY_NAME, tctId.getTctDisplayName());
        assertEquals(PARENT_NAME, tctId.getTctParentDisplayName());
    }

    @Test
    public void toCoral_givenAtaTestResult_returnsTctResult() {
        // GIVEN
        ATATestId ataTestId = ATATestId.builder()
                .withDisplayName(DISPLAY_NAME)
                .withParentDisplayName(PARENT_NAME)
                .build();
        ATATestResult ataTestResult = ATATestResult.builder()
                .withTestId(ataTestId)
                .withPassed(true)
                .withErrorMessage(ERROR_MESSAGE)
                .build();

        // WHEN
        TctResult tctResult = CoralModelATATestConverter.toCoral(ataTestResult);

        // THEN
        assertEquals(DISPLAY_NAME, tctResult.getTctId().getTctDisplayName());
        assertEquals(PARENT_NAME, tctResult.getTctId().getTctParentDisplayName());
        assertTrue(tctResult.isPassed());
        assertEquals(ERROR_MESSAGE, tctResult.getErrorMessage());
    }

    @Test
    public void toCoral_givenAtaTestSuiteId_returnsString() {
        // GIVEN
        ATATestSuiteId ataTestSuiteId = new ATATestSuiteId(SUITE_ID);

        // WHEN
        String testSuiteId = CoralModelATATestConverter.toCoral(ataTestSuiteId);

        // THEN
        assertEquals(SUITE_ID, testSuiteId);
    }

    @Test
    public void toCoral_givenAtaTestSuiteReport_returnsTctSuiteReport() {
        // GIVEN
        ATATestId ataTestId = ATATestId.builder()
                .withDisplayName(DISPLAY_NAME)
                .withParentDisplayName(PARENT_NAME)
                .build();
        ATATestResult ataTestResult = ATATestResult.builder()
                .withTestId(ataTestId)
                .withPassed(true)
                .withErrorMessage(ERROR_MESSAGE)
                .build();
        ATATestSuiteReport ataTestSuiteReport = new ATATestSuiteReport(new ATATestSuiteId(SUITE_ID),
                                                                       Arrays.asList(ataTestResult, ataTestResult));

        // WHEN
        TctSuiteReport tctSuiteReport = CoralModelATATestConverter.toCoral(ataTestSuiteReport);

        // THEN
        assertEquals(SUITE_ID, tctSuiteReport.getTctSuiteId());
        assertEquals(2, tctSuiteReport.getTctResultList().size());
        for (TctResult tctResult : tctSuiteReport.getTctResultList()) {
            assertEquals(DISPLAY_NAME, tctResult.getTctId().getTctDisplayName());
            assertEquals(PARENT_NAME, tctResult.getTctId().getTctParentDisplayName());
            assertTrue(tctResult.isPassed());
            assertEquals(ERROR_MESSAGE, tctResult.getErrorMessage());
        }
    }

    @Test
    public void fromCoral_givenTctId_returnsAtaTestId() {
        // GIVEN
        TctId tctId = TctId.builder()
                .withTctDisplayName(DISPLAY_NAME)
                .withTctParentDisplayName(PARENT_NAME)
                .build();

        // WHEN
        ATATestId ataTestId = CoralModelATATestConverter.fromCoral(tctId);

        // THEN
        assertEquals(DISPLAY_NAME, ataTestId.getDisplayName());
        assertEquals(PARENT_NAME, ataTestId.getParentDisplayName());
    }

    @Test
    public void fromCoral_givenTctResult_returnsAtaTestResult() {
        // GIVEN
        TctId tctId = TctId.builder()
                .withTctDisplayName(DISPLAY_NAME)
                .withTctParentDisplayName(PARENT_NAME)
                .build();
        TctResult tctResult = TctResult.builder()
                .withTctId(tctId)
                .withPassed(true)
                .withErrorMessage(ERROR_MESSAGE)
                .build();

        // WHEN
        ATATestResult ataTestResult = CoralModelATATestConverter.fromCoral(tctResult);

        // THEN
        assertEquals(DISPLAY_NAME, ataTestResult.getTestId().getDisplayName());
        assertEquals(PARENT_NAME, ataTestResult.getTestId().getParentDisplayName());
        assertTrue(ataTestResult.isPassed());
        assertEquals(ERROR_MESSAGE, ataTestResult.getErrorMessage().get());
    }

    @Test
    public void fromCoral_givenString_returnsAtaTestSuiteId() {
        // WHEN
        ATATestSuiteId testSuiteId = CoralModelATATestConverter.fromCoral(SUITE_ID);

        // THEN
        assertEquals(SUITE_ID, testSuiteId.getTestSuiteId());
    }

    @Test
    public void fromCoral_givenTctSuiteReport_returnsAtaTestSuiteReport() {
        // GIVEN
        TctId tctId = TctId.builder()
                .withTctDisplayName(DISPLAY_NAME)
                .withTctParentDisplayName(PARENT_NAME)
                .build();
        TctResult tctResult = TctResult.builder()
                .withTctId(tctId)
                .withPassed(true)
                .withErrorMessage(ERROR_MESSAGE)
                .build();
        TctSuiteReport tctSuiteReport = TctSuiteReport.builder()
                .withTctSuiteId(SUITE_ID)
                .withTctResultList(Arrays.asList(tctResult, tctResult)).build();

        // WHEN
        ATATestSuiteReport ataTestSuiteReport = CoralModelATATestConverter.fromCoral(tctSuiteReport);

        // THEN
        assertEquals(SUITE_ID, ataTestSuiteReport.getTestSuiteId().getTestSuiteId());
        assertEquals(2, ataTestSuiteReport.getResults().size());
        for (ATATestResult ataTestResult : ataTestSuiteReport.getResults()) {
            assertEquals(DISPLAY_NAME, ataTestResult.getTestId().getDisplayName());
            assertEquals(PARENT_NAME, ataTestResult.getTestId().getParentDisplayName());
            assertTrue(ataTestResult.isPassed());
            assertEquals(ERROR_MESSAGE, ataTestResult.getErrorMessage().get());
        }
    }
}