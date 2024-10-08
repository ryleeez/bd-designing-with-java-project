package com.amazon.ata.types;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackagingTest {
    private Material packagingMaterial = Material.CORRUGATE;
    private BigDecimal packagingLength = BigDecimal.valueOf(5.6);
    private BigDecimal packagingWidth = BigDecimal.valueOf(3.3);
    private BigDecimal packagingHeight = BigDecimal.valueOf(8.1);

    private Box box;

    @BeforeEach
    public void setUp() {
        box = new Box(packagingMaterial, packagingLength, packagingWidth, packagingHeight);
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
        assertFalse(canFit, "Item with longer length than package should not fit in the package.");
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
        assertFalse(canFit, "Item with longer width than package should not fit in the package.");
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
        assertFalse(canFit, "Item with longer height than package should not fit in the package.");
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
        assertFalse(canFit, "Item the same size as the package should not fit in the package.");
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
        assertTrue(canFit, "Item smaller than the package should fit in the package.");
    }

    @Test
    public void getMass_calculatesMass_returnsCorrectMass() {
        // GIVEN
        box = new Box(Material.CORRUGATE, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.valueOf(20));

        // WHEN
        BigDecimal mass = box.getMass();

        // THEN
        assertEquals(BigDecimal.valueOf(1000), mass,
            "Item smaller than the box should fit in the package.");
    }

    @Test
    public void equals_sameObject_isTrue() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);

        // WHEN
        boolean result = box.equals(box);

        // THEN
        assertTrue(result, "An object should be equal with itself.");
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);

        // WHEN
        boolean isEqual = box.equals(null);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal with null.");
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);
        Object other = "String type!";

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertFalse(isEqual, "A Packaging should not be equal to an object of a different type.");
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);
        Object other = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);

        // WHEN
        boolean isEqual = box.equals(other);

        // THEN
        assertTrue(isEqual, "Packaging with the same attributes should be equal.");
    }

    @Test
    public void hashCode_equalObjects_equalHash() {
        // GIVEN
        Box box = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);
        Box other = new Box(Material.CORRUGATE, packagingLength, packagingWidth, packagingHeight);

        // WHEN + THEN
        assertEquals(box.hashCode(), other.hashCode(), "Equal objects should have equal hashCodes");
    }
    @Test
    public void height_width_length_variables_removed_from_packaging_class() {
        Field[] fields = Packaging.class.getDeclaredFields();
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("height")), "height attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("width")),  "width attribute should be removed from Packaging class since it was moved to Box class");
        assertNotEquals(true, Arrays.stream(fields).anyMatch(f -> f.getName().equals("length")), "length attribute should be removed from Packaging class since it was moved to Box class");
        }
}
