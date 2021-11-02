package com.amazon.ata.test.tct.instrospection;

import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteRequest;
import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecuteTctSuiteHelperTest {

    private static final String TCT_SUITE_ID = "MT01";

    private ExecuteTctSuiteHelper testSuiteHelper;

    @BeforeEach
    public void setup() {
        testSuiteHelper = new ExecuteTctSuiteHelper();
    }

    @Test
    public void execute_requestWithTctSuiteId_responseContainsSameId() {
        // GIVEN
        ExecuteTctSuiteRequest request = ExecuteTctSuiteRequest.builder()
                .withTctSuiteId(TCT_SUITE_ID)
                .build();

        // WHEN
        ExecuteTctSuiteResponse response = testSuiteHelper.execute(request);

        // THEN
        assertEquals(TCT_SUITE_ID, response.getTctSuiteReport().getTctSuiteId(), "Expected testSuiteId " +
                "in response to match the testSuiteId in the request.");
    }
}
