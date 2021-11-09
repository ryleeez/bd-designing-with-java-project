package com.amazon.atacurriculumsustainabilityshipmentservicelambda.tct.tests;

import com.amazon.ata.test.helper.AtaTestHelper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PPT2IntrospectionTests {
    private static final String NEAR_PEER_TEST_TAG = "MT02_NEAR_PEER";
    private static final String PROJECT_BUDDY_TEST_TAG = "MT02_PROJECT_BUDDY";

    @Tag(PROJECT_BUDDY_TEST_TAG)
    @Test
    public void ppt2_getProjectBuddyFile_nonEmptyFileExists() {
        // GIVEN
        String fileName = "pb-preparedness-task2.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(fileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain project buddy " +
                "communication plan.", fileName));
    }

    @Tag(NEAR_PEER_TEST_TAG)
    @Test
    public void ppt2_getNearPeerFile_nonEmptyFileExists() {
        // GIVEN
        String fileName = "np-preparedness-task2.txt";

        // WHEN
        String content = AtaTestHelper.getFileContentFromResources(fileName);

        // THEN
        assertFalse(content.trim().isEmpty(), String.format("Expected file: %s to contain near peer " +
                "communication plan.", fileName));
    }
}
