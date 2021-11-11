package com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.seven;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.helpers.TctIntrospectionTest;

import com.amazonaws.services.shipmentservice.model.TctResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MasteryTaskSevenTasks extends TctIntrospectionTest {
    @BeforeClass
    public void setup() throws Exception {
        super.setup();
    }

    @Override
    public String getTestSuiteId() {
        return "MT07";
    }

    @Test(dataProvider = "TctResults")
    public void masteryTask7_runIntrospectionSuite_reportResults(TctResult result) {
        assertTrue(result.isPassed(), result.getErrorMessage() + "\n");
    }
}
