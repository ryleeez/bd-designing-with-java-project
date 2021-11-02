package com.amazon.atacurriculumsustainabilityshipmentservicelambda;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CarbonCostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.MonetaryCostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.WeightedCostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao.PackagingDAO;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.datastore.PackagingDatastore;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.service.ShipmentService;

public class App {
    /* don't instantiate me */
    private App() {}

    private static PackagingDatastore getPackagingDatastore() {
        return new PackagingDatastore();
    }

    private static PackagingDAO getPackagingDAO() {
        return new PackagingDAO(getPackagingDatastore());
    }

    /**
     * This will create WeightedCostStrategy composed of MonetaryCost and CarbonCost
     * with reasonable default weights.
     *
     * Use the {@link WeightedCostStrategy#builder()} to build with custom weights, or if we add
     * additional {@link CostStrategy}s.
     *
     * @return A WeightedCostStrategy composed of MonetaryCost and CarbonCost
     *         with default weights.
     */
    private static CostStrategy getCostStrategy() {
        return new WeightedCostStrategy(new MonetaryCostStrategy(), new CarbonCostStrategy());
    }

    public static ShipmentService getShipmentService() {
        return new ShipmentService(getPackagingDAO(), getCostStrategy());
    }
}
