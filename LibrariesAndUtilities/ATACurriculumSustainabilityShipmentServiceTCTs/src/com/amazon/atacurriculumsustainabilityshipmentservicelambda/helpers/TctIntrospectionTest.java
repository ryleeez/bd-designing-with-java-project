package com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers;

import com.amazon.ata.test.tct.TctResultExceptionIterator;

import com.amazonaws.services.shipmentservice.model.ExecuteTctSuiteRequest;
import com.amazonaws.services.shipmentservice.model.ExecuteTctSuiteResult;
import com.amazonaws.services.shipmentservice.model.TctSuiteReport;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

/**
 * Class that contains a data provider to get introspection results via the ExecuteTctSuite API.
 * The getTestSuiteId method needs to be overriden so the data provider can call to get results
 * for the appropriate test suite.
 */
public abstract class TctIntrospectionTest extends SustainabilityShipmentIntegrationTestBase {

    /**
     * Makes a call to the ExecuteTctSuite to run a test suite, which is determined by calling
     * getTestSuiteId().
     * @return a 2D Object Array - each Array has one object in it, a
     *         {@link com.amazonaws.services.shipmentservice.model.TctResult} object
     */
    @DataProvider(name = "TctResults")
    public Iterator<Object[]> getTctResults() {
        try {
            ExecuteTctSuiteRequest request = new ExecuteTctSuiteRequest().withTctSuiteId(getTestSuiteId());
            ExecuteTctSuiteResult result = shipmentServiceClient.executeTctSuite(request);
            TctSuiteReport report = result.getTctSuiteReport();

            return report.getTctResultList()
                .stream()
                .map(tctResult -> new Object[]{tctResult})
                .iterator();
        } catch (Exception e) {
            return new TctResultExceptionIterator(e);
        }
    }

    /**
     * Returns the testSuiteId the test class would like to execute.
     * @return the TestSuiteId for the test suite you would like to be run.
     */
    protected abstract String getTestSuiteId();
}
