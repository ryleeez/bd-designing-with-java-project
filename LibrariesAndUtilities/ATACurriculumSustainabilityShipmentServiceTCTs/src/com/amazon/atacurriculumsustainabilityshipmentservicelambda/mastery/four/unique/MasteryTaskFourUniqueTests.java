package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.four.unique;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MasteryTaskFourUniqueTests extends TctIntrospectionTest {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    public String getTestSuiteId() {
        return "MT04_UNIQUE";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask4_runUniqueIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
