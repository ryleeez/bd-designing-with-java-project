package com.amazon.atacurriculumsustainabilityshipmentservicelambda.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoxTest {

    private BigDecimal packagingLength = BigDecimal.valueOf(5.6);
    private BigDecimal packagingWidth = BigDecimal.valueOf(3.3);
    private BigDecimal packagingHeight = BigDecimal.valueOf(8.1);

    private Box box;

    @BeforeEach
    public void setUp() {
        box = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();
    }

    @Test
    public void canFitItem_itemLengthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(packagingLength.add(BigDecimal.ONE))
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean canFit = box.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer length than box should not fit in the package.");
    }

    @Test
    public void canFitItem_itemWidthTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth.add(BigDecimal.ONE))
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean canFit = box.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer width than box should not fit in the package.");
    }

    @Test
    public void canFitItem_itemHeightTooLong_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight.add(BigDecimal.ONE))
            .build();

        // WHEN
        boolean canFit = box.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item with longer height than box should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSameSizeAsBox_doesNotFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean canFit = box.canFitItem(item);

        // THEN
        assertFalse(canFit, "Item the same size as the box should not fit in the package.");
    }

    @Test
    public void canFitItem_itemSmallerThanBox_doesFit() {
        // GIVEN
        Item item = Item.builder()
            .withLength(packagingLength.subtract(BigDecimal.ONE))
            .withWidth(packagingWidth.subtract(BigDecimal.ONE))
            .withHeight(packagingHeight.subtract(BigDecimal.ONE))
            .build();

        // WHEN
        boolean canFit = box.canFitItem(item);

        // THEN
        assertTrue(canFit, "Item smaller than the box should fit in the package.");
    }

    @Test void getMass_calculatesMassCorrectly() {
        // GIVEN
        box = Box.builder()
            .withLength(BigDecimal.TEN)
            .withWidth(BigDecimal.TEN)
            .withHeight(BigDecimal.valueOf(20))
            .build();

        // WHEN
        BigDecimal mass = box.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(1000), mass,
            "Item smaller than the box should fit in the package.");
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Object other = box;

        // WHEN
        boolean result = box.equals(other);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Object other = null;

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "A Box should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Object other = "String type!";

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "A Box should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameDimensions_returnsTrue() {
        // GIVEN
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertTrue(isEqual, "Box with the same length, width, and height are equal.");
    }

    @Test
    public void equals_differentLength_returnsFalse() {
        // GIVEN
        Object other = Box.builder()
            .withLength(packagingLength.add(BigDecimal.ONE))
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "Boxes with different lengths are not equal.");
    }

    @Test
    public void equals_differentWidth_returnsFalse() {
        // GIVEN
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth.add(BigDecimal.ONE))
            .withHeight(packagingHeight)
            .build();

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "Boxes with different widths are not equal.");
    }

    @Test
    public void equals_differentHeight_returnsFalse() {
        // GIVEN
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight.add(BigDecimal.ONE))
            .build();

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "Boxes with different heights are not equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        int boxHashCode = box.hashCode();
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertEquals(boxHashCode, otherHashCode, "Equal objects should have equal hashCodes");
    }

    @Test
    public void hashCode_differentLengths_differentHashCode() {
        // GIVEN
        int boxHashCode = box.hashCode();
        Object other = Box.builder()
            .withLength(packagingLength.add(BigDecimal.ONE))
            .withWidth(packagingWidth)
            .withHeight(packagingHeight)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(boxHashCode, otherHashCode, "Boxes with different lengths should have different hashCodes");
    }

    @Test
    public void hashCode_differentWidths_differentHashCode() {
        // GIVEN
        int boxHashCode = box.hashCode();
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth.add(BigDecimal.ONE))
            .withHeight(packagingHeight)
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(boxHashCode, otherHashCode, "Boxes with different widths should have different hashCodes");
    }

    @Test
    public void hashCode_differentHeights_differentHashCode() {
        // GIVEN
        int boxHashCode = box.hashCode();
        Object other = Box.builder()
            .withLength(packagingLength)
            .withWidth(packagingWidth)
            .withHeight(packagingHeight.add(BigDecimal.ONE))
            .build();

        // WHEN
        int otherHashCode = other.hashCode();

        // THEN
        assertNotEquals(boxHashCode, otherHashCode, "Boxes with different heights should have different hashCodes");
    }
}
