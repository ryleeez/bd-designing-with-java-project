package com.amazon.atacurriculumsustainabilityshipmentservicelambda.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FcPackagingOptionTest {
    private FulfillmentCenter fc = new FulfillmentCenter("123");
    private Packaging packaging = PolyBag.builder().withVolume(BigDecimal.ONE).build();

    private FcPackagingOption fcPackagingOption;

    @BeforeEach
    public void setUp() {
        fcPackagingOption = new FcPackagingOption(fc, packaging);
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Object other = fcPackagingOption;

        // WHEN
        boolean result = fcPackagingOption.equals(other);

        // THEN
        assertTrue(result, "An object is equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = fcPackagingOption.equals(other);

        // THEN
        assertFalse(isEqual, "An FcPackagingOption is not equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = fcPackagingOption.equals(other);

        // THEN
        assertFalse(isEqual, "An FcPackagingOption is not equal to an object of a different type.");
    }

    @Test
    public void equals_sameFcSamePackaging_returnsTrue() {
        // GIVEN
        Object other = new FcPackagingOption(fc, packaging);

        // WHEN
        boolean isEqual = fcPackagingOption.equals(other);

        // THEN
        assertTrue(isEqual, "FcPackagingOption with the same FulfillmentCenter and Packaging are equal.");
    }

    @Test
    public void equals_differentFc_returnsFalse() {
        // GIVEN
        FulfillmentCenter otherFc = new FulfillmentCenter("DIFFERENT");
        Object other = new FcPackagingOption(otherFc, packaging);

        // WHEN
        boolean isEqual = fcPackagingOption.equals(other);

        // THEN
        assertFalse(isEqual, "FcPackagingOptions with different FulfillmentCenters are not equal.");
    }

    @Test
    public void equals_differentPackaging_returnsFalse() {
        // GIVEN
        Packaging otherPackaging = PolyBag.builder()
            .withVolume(BigDecimal.ZERO)
            .build();
        Object other = new FcPackagingOption(fc, otherPackaging);

        // WHEN
        boolean isEqual = fcPackagingOption.equals(other);

        // THEN
        assertFalse(isEqual, "FcPackagingOptions with different Packagings are not equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        int optionHashCode = fcPackagingOption.hashCode();
        Object other = new FcPackagingOption(fc, packaging);

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertEquals(optionHashCode, otherHashCode, "Equal objects should have equal hashCodes");
    }

    @Test
    public void hashCode_differentFc_differentHashCode() {
        // GIVEN
        int optionHashCode = fcPackagingOption.hashCode();
        FulfillmentCenter otherFc = new FulfillmentCenter("DIFFERENT");
        Object otherOption = new FcPackagingOption(otherFc, packaging);

        // WHEN
        int otherHashCode = otherOption.hashCode();

        // THEN
        assertNotEquals(optionHashCode, otherHashCode,
            "FcPackagingOptions with different FulfillmentCenters should have different hashCode.");
    }

    @Test
    public void hashCode_differentPackaging_differentHashCode() {
        // GIVEN
        int optionHashCode = fcPackagingOption.hashCode();
        Packaging otherPackaging = PolyBag.builder()
            .withVolume(BigDecimal.ZERO)
            .build();
        Object otherOption = new FcPackagingOption(fc, otherPackaging);

        // WHEN
        int otherHashCode = otherOption.hashCode();

        // THEN
        assertNotEquals(optionHashCode, otherHashCode,
            "FcPackagingOptions with different Packagings should have different hashCode.");
    }
}
