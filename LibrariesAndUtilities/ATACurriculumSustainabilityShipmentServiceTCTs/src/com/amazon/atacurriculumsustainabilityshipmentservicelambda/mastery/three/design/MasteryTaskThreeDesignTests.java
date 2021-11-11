package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.three.design;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MasteryTaskThreeDesignTests extends TctIntrospectionTest {
    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    protected String getTestSuiteId() {
        return "MT03_DESIGN";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask3_runDesignIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
