package com.amazon.ata.test.tct.instrospection;

import com.amazon.ata.test.tct.introspection.types.TctId;
import com.amazon.ata.test.tct.introspection.types.TctResult;
import com.amazon.ata.test.tct.introspection.types.TctSuiteReport;
import com.amazon.ata.test.types.ATATestId;
import com.amazon.ata.test.types.ATATestResult;
import com.amazon.ata.test.types.ATATestSuiteId;
import com.amazon.ata.test.types.ATATestSuiteReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for converting objects between ATATest objects and the ATA test coral model.
 */
public class CoralModelATATestConverter {
    private CoralModelATATestConverter() {}

    /**
     * Converts an ATATestId to a TctId for use by Coral.
     * @param ataTestId the service object to be converted
     * @return the object for use by coral
     */
    public static TctId toCoral(ATATestId ataTestId) {
        return TctId.builder()
                .withTctDisplayName(ataTestId.getDisplayName())
                .withTctParentDisplayName(ataTestId.getParentDisplayName())
                .build();
    }

    /**
     * Converts an ATATestResult to a TctResult for use by Coral.
     * @param result the service object to be converted
     * @return the object for use by coral
     */
    public static TctResult toCoral(ATATestResult result) {
        TctResult.Builder tctResultBuilder = TctResult.builder()
                .withTctId(toCoral(result.getTestId()))
                .withPassed(result.isPassed());
        result.getErrorMessage().ifPresent(tctResultBuilder::withErrorMessage);
        return tctResultBuilder.build();
    }

    /**
     * Converts an ATATestSuiteId to a String for use by Coral.
     * @param testSuiteId the service object to be converted
     * @return the object for use by coral
     */
    public static String toCoral(ATATestSuiteId testSuiteId) {
        return testSuiteId.getTestSuiteId();
    }

    /**
     * Converts an ATATestSuiteReport to a TctSuiteReport for use by Coral.
     * @param report the service object to be converted
     * @return the object for use by coral
     */
    public static TctSuiteReport toCoral(ATATestSuiteReport report) {
        List<TctResult> results = new ArrayList<>();
        for (ATATestResult result : report.getResults()) {
            results.add(toCoral(result));
        }
        return TctSuiteReport.builder()
                .withTctSuiteId(toCoral(report.getTestSuiteId()))
                .withTctResultList(results)
                .build();
    }

    /**
     * Converts a coral TctId to an ATATestId for use in the service.
     * @param tctId the coral object to be converted
     * @return the object for use by the service
     */
    public static ATATestId fromCoral(TctId tctId) {
        return ATATestId.builder()
                .withDisplayName(tctId.getTctDisplayName())
                .withParentDisplayName(tctId.getTctParentDisplayName())
                .build();
    }

    /**
     * Converts a coral TctResult to an ATATestResult for use in the service.
     * @param result the coral object to be converted
     * @return the object for use by the service
     */
    public static ATATestResult fromCoral(TctResult result) {
        return ATATestResult.builder()
                .withTestId(fromCoral(result.getTctId()))
                .withPassed(result.isPassed())
                .withErrorMessage(result.getErrorMessage())
                .build();
    }

    /**
     * Converts a String to an ATATestSuideId for use in the service.
     * @param testSuiteId the coral object to be converted
     * @return the object for use by the service
     */
    public static ATATestSuiteId fromCoral(String testSuiteId) {
        return new ATATestSuiteId(testSuiteId);
    }

    /**
     * Converts a coral TctSuiteReport to an ATATestSuiteReport for use in the service.
     * @param report the coral object to be converted
     * @return the object for use by the service
     */
    public static ATATestSuiteReport fromCoral(TctSuiteReport report) {
        List<ATATestResult> results = new ArrayList<>();
        for (TctResult result : report.getTctResultList()) {
            results.add(fromCoral(result));
        }
        return new ATATestSuiteReport(fromCoral(report.getTctSuiteId()), results);
    }
}
