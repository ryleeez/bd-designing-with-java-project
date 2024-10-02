package com.amazon.ata.types;

import java.math.BigDecimal;
import java.util.Objects;

public class PolyBag extends Packaging {
    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
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
        double volumeDouble = volume.doubleValue();
        long mass = Math.round(Math.ceil(Math.sqrt(volumeDouble) * 0.6));
        return BigDecimal.valueOf(mass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PolyBag polyBag = (PolyBag) o;
        return Objects.equals(volume, polyBag.volume) &&
                Objects.equals(getMaterial(), polyBag.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume, getMaterial());
    }
}
