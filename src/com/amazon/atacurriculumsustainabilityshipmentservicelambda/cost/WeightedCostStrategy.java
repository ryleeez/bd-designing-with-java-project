package com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentCost;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {
    private static final BigDecimal MONETARY_COST_STRATEGY_WEIGHT = new BigDecimal("0.8");
    private static final BigDecimal CARBON_COST_STRATEGY_WEIGHT = new BigDecimal("0.2");

    private Map<BigDecimal, List<CostStrategy>> weightedStrategies;

    /**
     * Construct a weighted cost strategy with default weights.
     * @param monetaryCostStrategy the {@link MonetaryCostStrategy} to use.
     * @param carbonCostStrategy the {@link CarbonCostStrategy} to use.
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryCostStrategy,
                                CarbonCostStrategy carbonCostStrategy) {
        weightedStrategies = new HashMap<>();
        weightedStrategies.put(MONETARY_COST_STRATEGY_WEIGHT, ImmutableList.of(monetaryCostStrategy));
        weightedStrategies.put(CARBON_COST_STRATEGY_WEIGHT, ImmutableList.of(carbonCostStrategy));
    }

    /**
     * Constructs a weighted cost strategy using its {@link Builder}.
     */
    private WeightedCostStrategy(Builder builder) {
        this.weightedStrategies = builder.weightedStrategies;
    }

    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<BigDecimal, List<CostStrategy>> entry : weightedStrategies.entrySet()) {
            BigDecimal weight = entry.getKey();
            List<CostStrategy> strategies = entry.getValue();
            for (CostStrategy strategy : strategies) {
                BigDecimal strategyCost = strategy.getCost(shipmentOption).getCost();
                BigDecimal weightedCost = weight.multiply(strategyCost);
                totalCost = totalCost.add(weightedCost);
            }
        }
        return new ShipmentCost(shipmentOption, totalCost);
    }

    /**
     * Returns a Builder to create a WeightedCostStrategy from any combination of CostStrategys and weights.
     * @return a Builder to create a WeightedCostStrategy from any combination of CostStrategys and weights.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Optional builder implementation.
     */
    public static final class Builder {
        private Map<BigDecimal, List<CostStrategy>> weightedStrategies = new HashMap<>();

        private Builder() {
        }

        /**
         * Adds a CostStrategy that will be multiplied by the given weight when calculating the weighted cost.
         * @param strategy A strategy to consider when calculating weighted cost.
         * @param weight The factor to weight this strategy's cost by.
         * @return The Builder, for chaining.
         */
        public Builder addStrategyWithWeight(CostStrategy strategy, BigDecimal weight) {
            List<CostStrategy> strategies = weightedStrategies.getOrDefault(weight, new ArrayList<>());
            strategies.add(strategy);
            weightedStrategies.put(weight, strategies);

            return this;
        }

        /**
         * Creates a WeightedCostStrategy using the previously-specified strategy weights.
         *
         * @return a WeightedCostStrategy using the previously-specified strategy weights.
         * @throws InvalidParameterException if no CostStrategy has been provided.
         */
        public WeightedCostStrategy build() throws InvalidParameterException {
            if (weightedStrategies.isEmpty()) {
                throw new InvalidParameterException("Cannot create an empty WeightedCostStrategy!");
            }
            return new WeightedCostStrategy(this);
        }
    }
}
