package com.amazon.ata.types;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PolyBag extends Packaging{
    private BigDecimal volume;

    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemVolume = item.getLength().multiply(item.getWidth()).multiply(item.getHeight());
        return this.volume.compareTo(itemVolume) > 0;
    }

    @Override
    public BigDecimal getMass() {
        double mass = Math.ceil(Math.sqrt(volume.doubleValue()) * 0.6);
        return BigDecimal.valueOf(mass).setScale(0, RoundingMode.CEILING);
    }
}
