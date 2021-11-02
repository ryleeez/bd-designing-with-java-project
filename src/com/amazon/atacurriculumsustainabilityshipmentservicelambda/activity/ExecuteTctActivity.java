package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.ata.test.tct.instrospection.ExecuteTctSuiteHelper;
import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteRequest;
import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteResponse;

import com.amazon.bones.lambdarouter.LambdaActivityBase;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the TCT functionality.
 *
 * This is implementation of the ExecuteTctSuite activity. It handles ExecuteTctSuite requests by running
 * the appropriate tcts and returning a summary of their results.
 */
public class ExecuteTctActivity extends LambdaActivityBase<ExecuteTctSuiteRequest, ExecuteTctSuiteResponse> {

    private ExecuteTctSuiteHelper tctSuiteHelper;

    /**
     * Constructs an ExecuteTctActivity with a <code>ExecuteTestSuiteHelper</code>.
     * @param tctSuiteHelper handles execution of <code>ExecuteTctSuiteRequest</code>s
     */
    public ExecuteTctActivity(ExecuteTctSuiteHelper tctSuiteHelper) {
        super(ExecuteTctSuiteRequest.class);
        this.tctSuiteHelper = tctSuiteHelper;
    }

    @Override
    public ExecuteTctSuiteResponse handleRequest(ExecuteTctSuiteRequest executeTctSuiteRequest) {
        return tctSuiteHelper.execute(executeTctSuiteRequest);
    }

}
