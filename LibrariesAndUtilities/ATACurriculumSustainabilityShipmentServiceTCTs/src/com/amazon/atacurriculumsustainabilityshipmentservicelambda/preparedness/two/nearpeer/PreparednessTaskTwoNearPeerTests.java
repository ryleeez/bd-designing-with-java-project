package com.amazon.atacurriculumsustainabilityshipmentservicelambda.preparedness.two.nearpeer;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class PreparednessTaskTwoNearPeerTests extends TctIntrospectionTest {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    protected String getTestSuiteId() {
        return "MT02_NEAR_PEER";
    }

    @Test(dataProvider = "TctResults")
    public void preparednessTask2_runIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
