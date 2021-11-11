package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.five.weighted;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MasteryTask5WeightedTests extends TctIntrospectionTest {
    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    public String getTestSuiteId() {
        return "MT05_WEIGHTED";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask5_runPrepIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
