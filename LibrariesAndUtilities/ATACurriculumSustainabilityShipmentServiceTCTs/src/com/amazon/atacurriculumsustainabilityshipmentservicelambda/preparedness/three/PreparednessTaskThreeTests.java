package com.amazon.atacurriculumsustainabilityshipmentservicelambda.preparedness.three;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PreparednessTaskThreeTests extends TctIntrospectionTest {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    protected String getTestSuiteId() {
        return "PPT03";
    }

    @Test(dataProvider = "TctResults")
    public void preparednessTask3_runIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
