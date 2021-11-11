package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.two.local;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MasteryTaskTwoLocalTests extends TctIntrospectionTest {

    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    protected String getTestSuiteId() {
        return "MT02_LOCAL";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask2_runIntrospectionSuite_reportResults(TctResult result) {
        Assert.assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }

}
