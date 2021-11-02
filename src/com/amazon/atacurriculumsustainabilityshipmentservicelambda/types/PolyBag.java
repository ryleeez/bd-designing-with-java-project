package com.amazon.atacurriculumsustainabilityshipmentservicelambda.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {

    /**
     * This polybag's volume.
     */
    private BigDecimal volume;

    /**
     * Instantiates a new PolyBag object.
     * @param volume - the volume of the polybag
     */
    public PolyBag(BigDecimal volume) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = volume;
    }

    private PolyBag(Builder builder) {
        super(Material.LAMINATED_PLASTIC);
        this.volume = builder.volume;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemVolume = item.getLength().multiply(item.getWidth()).multiply(item.getHeight());
        return this.volume.compareTo(itemVolume) > 0;
    }

    @Override
    public BigDecimal getMass() {
        return BigDecimal.valueOf(Math.ceil(Math.sqrt(this.volume.doubleValue()) * 0.6));
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

        PolyBag polyBag = (PolyBag) o;
        return Objects.equals(getVolume(), polyBag.getVolume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVolume(), super.hashCode());
    }

    @Override
    public String toString() {
        return "PolyBag{" +
            "volume=" + volume +
            '}';
    }

    /**
     * Returns a new PolyBag.Builder object for constructing a PolyBag.
     *
     * @return new builder ready for constructing a PolyBag
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * {@code PolyBag} builder static inner class.
     * NOTE: Implementing builder classes is optional.
     */
    public static final class Builder {
        private BigDecimal volume;

        //CHECKSTYLE:OFF:HiddenField
        private Builder() {
        }

        /**
         * Sets the {@code volume} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param volume the {@code length} to set
         * @return a reference to this Builder
         */
        public Builder withVolume(BigDecimal volume) {
            this.volume = volume;
            return this;
        }

        /**
         * Returns a {@code PolyBag} built from the parameters previously set.
         *
         * @return a {@code PolyBag} built with parameters of this {@code PolyBag.Builder}
         */
        public PolyBag build() {
            return new PolyBag(this);
        }
    }
}
