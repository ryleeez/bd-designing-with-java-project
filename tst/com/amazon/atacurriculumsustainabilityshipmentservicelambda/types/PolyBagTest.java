package com.amazon.atacurriculumsustainabilityshipmentservicelambda.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PolyBagTest {

    private BigDecimal packagingVolume = BigDecimal.valueOf(1000);

    private BigDecimal itemLength = BigDecimal.valueOf(10);
    private BigDecimal itemWidth = BigDecimal.valueOf(10);
    private BigDecimal itemHeight = BigDecimal.valueOf(10);

    private PolyBag polybag;

    @BeforeEach
    public void setUp() {
        polybag = PolyBag.builder()
            .withVolume(packagingVolume)
            .build();
    }

    @Test
    public void canFitItem_itemVolumeTooLarge_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(itemLength.add(BigDecimal.ONE))
            .withWidth(itemWidth)
            .withHeight(itemHeight)
            .build();

        // WHEN
        boolean canFit = polybag.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with greater volume than polybag should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSameVolumeAsPolyBag_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(itemLength)
            .withWidth(itemWidth)
            .withHeight(itemHeight)
            .build();

        // WHEN
        boolean canFit = polybag.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with the same volume as the polybag should not fit in the package.");
    }

    @Test
    public void canFitItem_itemLessVolumeThanPolyBag_doesFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(itemLength.subtract(BigDecimal.ONE))
            .withWidth(itemWidth)
            .withHeight(itemHeight)
            .build();

        // WHEN
        boolean canFit = polybag.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the polybag should fit in the package.");
    }

    @Test void getMass_calculatesMassCorrectly() {
        // GIVEN
        polybag = PolyBag.builder()
            .withVolume(BigDecimal.valueOf(2000))
            .build();

        // WHEN
        BigDecimal mass = polybag.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(27.0), mass, "Item smaller than the box should fit in the package.");
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Object other = polybag;

        // WHEN
        boolean result = polybag.equals(other);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = polybag.equals(other);

        // THEN
        assertFalse(isEqual, "A PolyBag should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = polybag.equals(other);

        // THEN
        assertFalse(isEqual, "A PolyBag should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameDimensions_returnsTrue() {
        // GIVEN
        Object other = PolyBag.builder()
            .withVolume(packagingVolume)
            .build();

        // WHEN
        boolean isEqual = polybag.equals(other);

        // THEN
        assertTrue(isEqual, "PolyBags with the same volume should be equal.");
    }

    @Test
    public void equals_differentVolumes_returnsFalse() {
        // GIVEN
        Object other = PolyBag.builder()
            .withVolume(packagingVolume.add(BigDecimal.ONE))
            .build();

        // WHEN
        boolean isEqual = polybag.equals(other);

        // THEN
        assertFalse(isEqual, "PolyBags with different volumes should not be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        int boxHashCode = polybag.hashCode();
        Object other = PolyBag.builder()
            .withVolume(packagingVolume)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertEquals(boxHashCode, otherHashCode, "Equal objects should have equal hashCodes");
    }

    @Test
    public void hashCode_differentVolumes_differentHashCode() {
        // GIVEN
        int boxHashCode = polybag.hashCode();
        Object other = PolyBag.builder()
            .withVolume(packagingVolume.add(BigDecimal.ONE))
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(boxHashCode, otherHashCode, "PolyBags with different volumes should have different hashCodes");
    }
}
