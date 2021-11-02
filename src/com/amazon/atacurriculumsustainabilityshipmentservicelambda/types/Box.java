package com.amazon.atacurriculumsustainabilityshipmentservicelambda.types;

import java.math.BigDecimal;
import java.util.Objects;

public class Box extends Packaging {

    /**
     * This box's length.
     */
    private BigDecimal length;

    /**
     * This box's smallest dimension.
     */
    private BigDecimal width;

    /**
     * This box's largest dimension.
     */
    private BigDecimal height;

    /**
     * Instantiates a new Box object.
     * @param length - the length of the box
     * @param width - the width of the box
     * @param height - the height of the box
     */
    public Box(BigDecimal length, BigDecimal width, BigDecimal height) {
        super(Material.CORRUGATE);
        this.length = length;
        this.width = width;
        this.height = height;
    }

    private Box(Builder builder) {
        super(Material.CORRUGATE);
        length = builder.length;
        width = builder.width;
        height = builder.height;
    }

    /**
     * Returns a new Box.Builder object for constructing a Box.
     *
     * @return new builder ready for constructing a Box
     */
    public static Builder builder() {
        return new Builder();
    }

    public BigDecimal getLength() {
        return length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    @Override
    public boolean canFitItem(Item item) {
        return this.length.compareTo(item.getLength()) > 0 &&
            this.width.compareTo(item.getWidth()) > 0 &&
            this.height.compareTo(item.getHeight()) > 0;
    }

    @Override
    public BigDecimal getMass() {
        BigDecimal two = BigDecimal.valueOf(2);

        // For simplicity, we ignore overlapping flaps
        BigDecimal endsArea = length.multiply(width).multiply(two);
        BigDecimal shortSidesArea = length.multiply(height).multiply(two);
        BigDecimal longSidesArea = width.multiply(height).multiply(two);

        return endsArea.add(shortSidesArea).add(longSidesArea);
    }

    @Override
    public boolean equals(Object o) {
        // Can't be equal to null
        if (o == null) {
            return false;
        }

        // Referentially equal
        if (this == o) {
            return true;
        }

        // Check if it's a different type
        if (getClass() != o.getClass()) {
            return false;
        }

        // Only include this check if the class you are
        // implementing equals in extends another class.
        if (!super.equals(o)) {
            return false;
        }

        Box box = (Box) o;
        return Objects.equals(getLength(), box.getLength()) &&
            Objects.equals(getWidth(), box.getWidth()) &&
            Objects.equals(getHeight(), box.getHeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLength(), getWidth(), getHeight(), super.hashCode());
    }

    @Override
    public String toString() {
        return "Box{" +
            "length=" + length +
            ", width=" + width +
            ", height=" + height +
            '}';
    }

    /**
     * {@code Box} builder static inner class.
     *
     * NOTE: Implementing builder classes is optional.
     */
    public static final class Builder {
        private BigDecimal length;
        private BigDecimal width;
        private BigDecimal height;

        //CHECKSTYLE:OFF:HiddenField
        private Builder() {
        }

        /**
         * Sets the {@code length} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param length the {@code length} to set
         * @return a reference to this Builder
         */
        public Builder withLength(BigDecimal length) {
            this.length = length;
            return this;
        }

        /**
         * Sets the {@code width} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param width the {@code width} to set
         * @return a reference to this Builder
         */
        public Builder withWidth(BigDecimal width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the {@code height} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param height the {@code height} to set
         * @return a reference to this Builder
         */
        public Builder withHeight(BigDecimal height) {
            this.height = height;
            return this;
        }

        /**
         * Returns a {@code Box} built from the parameters previously set.
         *
         * @return a {@code Box} built with parameters of this {@code Box.Builder}
         */
        public Box build() {
            return new Box(this);
        }
    }
}
