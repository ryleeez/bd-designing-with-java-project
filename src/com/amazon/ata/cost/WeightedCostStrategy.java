package com.amazon.ata.cost;

import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {
    private static final int SCALE = 2;
    private final Map<BigDecimal, CostStrategy> weightedStrategies;

    /**
     * Constructs a WeightedCostStrategy with the given map of weights to strategies.
     *
     * @param weightedStrategies A map where keys are weights and values are corresponding cost strategies
     */
    public WeightedCostStrategy(Map<BigDecimal, CostStrategy> weightedStrategies) {
        this.weightedStrategies = new HashMap<>(weightedStrategies);
        validateWeights();
    }

    private void validateWeights() {
        BigDecimal totalWeight = weightedStrategies.keySet().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (totalWeight.compareTo(BigDecimal.ONE) != 0) {
            throw new IllegalArgumentException("Weights must sum to 1");
        }
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        BigDecimal totalWeightedCost = weightedStrategies.entrySet().stream()
                .map(entry -> {
                    BigDecimal weight = entry.getKey();
                    CostStrategy strategy = entry.getValue();
                    return strategy.getCost(shipmentOption).getCost().multiply(weight);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(SCALE, RoundingMode.HALF_UP);

        return new ShipmentCost(shipmentOption, totalWeightedCost);
    }

    public static class Builder {
        private final Map<BigDecimal, CostStrategy> weightedStrategies = new HashMap<>();

        public Builder addStrategyWithWeight(CostStrategy strategy, BigDecimal weight) {
            weightedStrategies.put(weight, strategy);
            return this;
        }

        public WeightedCostStrategy build() {
            return new WeightedCostStrategy(weightedStrategies);
        }
    }
}
