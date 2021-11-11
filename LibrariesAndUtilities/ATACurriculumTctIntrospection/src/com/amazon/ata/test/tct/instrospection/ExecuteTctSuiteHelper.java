package com.amazon.ata.test.tct.instrospection;

import com.amazon.ata.test.junit.ATATestSuiteRunner;
import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteRequest;
import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteResponse;
import com.amazon.ata.test.tct.introspection.types.TctSuiteReport;
import com.amazon.ata.test.types.ATATestSuiteId;
import com.amazon.ata.test.types.ATATestSuiteReport;

/**
 * Class that can be used from a coral service package to forward and execute an ExecuteTctSuiteRequest. The Response
 * will be populated and returned.
 */
public class ExecuteTctSuiteHelper {

    /**
     * Executes a ExecuteTctSuiteRequest and wraps results in a ExecuteTctSuiteResponse.
     * @param request contains the tctSuiteId to execute
     * @return the results of executing the test suite corresponding to tctSuiteId
     */
    public ExecuteTctSuiteResponse execute(ExecuteTctSuiteRequest request) {
        ATATestSuiteId testSuiteId = CoralModelATATestConverter.fromCoral(request.getTctSuiteId());

        ATATestSuiteReport report = ATATestSuiteRunner.execute(testSuiteId);

        TctSuiteReport coralReport = CoralModelATATestConverter.toCoral(report);

        return ExecuteTctSuiteResponse.builder()
                .withTctSuiteReport(coralReport)
                .build();
    }
}
