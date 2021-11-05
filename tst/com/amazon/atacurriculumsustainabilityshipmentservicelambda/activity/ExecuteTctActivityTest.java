package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

//import com.amazon.ata.test.tct.instrospection.ExecuteTctSuiteHelper;
//import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteRequest;
//import com.amazon.ata.test.tct.introspection.types.ExecuteTctSuiteResponse;
//import com.amazon.ata.test.tct.introspection.types.TctId;
//import com.amazon.ata.test.tct.introspection.types.TctResult;
//import com.amazon.ata.test.tct.introspection.types.TctSuiteReport;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ExecuteTctActivityTest {
//
//    private static final String TCT_SUITE_ID = "MT01";
//    private static final TctId TCT_ID = TctId.builder()
//        .withTctDisplayName("mtXXX_functionality_testCase_expectedResults")
//        .withTctParentDisplayName("TestClass")
//        .build();
//    private static final TctResult RESULT = TctResult
//        .builder()
//        .withTctId(TCT_ID)
//        .withErrorMessage("The test has failed.")
//        .withPassed(false)
//        .build();
//    private static final List<TctResult> RESULTS = ImmutableList.of(RESULT);
//    private static final TctSuiteReport REPORT = TctSuiteReport
//        .builder()
//        .withTctSuiteId(TCT_SUITE_ID)
//        .withTctResultList(RESULTS)
//        .build();
//    @Mock
//    private ExecuteTctSuiteHelper testSuiteHelper;
//
//    private ExecuteTctActivity activity;
//
//    @BeforeEach
//    void setUp() {
//        initMocks(this);
//
//        activity = new ExecuteTctActivity(testSuiteHelper);
//    }
//
//    @Test
//    public void handleRequest_requestForwarded_responseReturned() {
//        // GIVEN
//        ExecuteTctSuiteRequest request = ExecuteTctSuiteRequest
//            .builder()
//            .withTctSuiteId(TCT_SUITE_ID)
//            .build();
//        ExecuteTctSuiteResponse expectedResponse = ExecuteTctSuiteResponse
//            .builder()
//            .withTctSuiteReport(REPORT)
//            .build();
//        when(testSuiteHelper.execute(request)).thenReturn(expectedResponse);
//
//        // WHEN
//        ExecuteTctSuiteResponse response = activity.handleRequest(request);
//
//        // THEN
//        assertEquals(expectedResponse.getTctSuiteReport().getTctSuiteId(),
//            response.getTctSuiteReport().getTctSuiteId(), "Expected response to have same TestSuiteId as " +
//                "in the request");
//        assertTrue(expectedResponse.getTctSuiteReport().getTctResultList().contains(RESULT),
//            "Expected response to contain TctResult returned by the ExecuteTestSuiteHelper");
//    }
}
