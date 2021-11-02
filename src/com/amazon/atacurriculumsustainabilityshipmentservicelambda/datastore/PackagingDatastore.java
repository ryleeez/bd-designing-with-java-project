package com.amazon.atacurriculumsustainabilityshipmentservicelambda.datastore;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Box;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FcPackagingOption;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Packaging;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.PolyBag;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Stores all configured packaging pairs for all fulfillment centers.
 */
public class PackagingDatastore {

    /**
     * The stored pairs of fulfillment centers to the packaging options they support.
     */
    private final List<FcPackagingOption> fcPackagingOptions = Arrays.asList(
        // Box
        createBoxFcPackagingOption("IND1", "10", "10", "10"),
        createBoxFcPackagingOption("ABE2", "20", "20", "20"),
        createBoxFcPackagingOption("ABE2", "40", "40", "40"),
        createBoxFcPackagingOption("YOW4", "10", "10", "10"),
        createBoxFcPackagingOption("YOW4", "20", "20", "20"),
        createBoxFcPackagingOption("YOW4", "60", "60", "60"),
        createBoxFcPackagingOption("IAD2", "20", "20", "20"),
        createBoxFcPackagingOption("IAD2", "20", "20", "20"),
        createBoxFcPackagingOption("PDX1", "40", "40", "40"),
        createBoxFcPackagingOption("PDX1", "60", "60", "60"),
        createBoxFcPackagingOption("PDX1", "60", "60", "60"),

        // PolyBags
        createPolyBagFcPackagingOption("IAD2", "2000"),
        createPolyBagFcPackagingOption("IAD2", "10000"),
        createPolyBagFcPackagingOption("IAD2", "5000"),
        createPolyBagFcPackagingOption("YOW4", "2000"),
        createPolyBagFcPackagingOption("YOW4", "5000"),
        createPolyBagFcPackagingOption("YOW4", "10000"),
        createPolyBagFcPackagingOption("IND1", "2000"),
        createPolyBagFcPackagingOption("IND1", "5000"),
        createPolyBagFcPackagingOption("ABE2", "2000"),
        createPolyBagFcPackagingOption("ABE2", "6000"),
        createPolyBagFcPackagingOption("PDX1", "5000"),
        createPolyBagFcPackagingOption("PDX1", "10000"),
        createPolyBagFcPackagingOption("YOW4", "5000")
    );

    /**
     * Create fulfillment center packaging option from provided parameters.
     */
    private FcPackagingOption createBoxFcPackagingOption(String fcCode,
                                                         String length, String width, String height) {
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);
        Packaging packaging = new Box(new BigDecimal(length), new BigDecimal(width),
            new BigDecimal(height));

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }

    private FcPackagingOption createPolyBagFcPackagingOption(String fcCode, String volume) {
        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(fcCode);
        Packaging packaging = new PolyBag(new BigDecimal(volume));

        return new FcPackagingOption(fulfillmentCenter, packaging);
    }

    public List<FcPackagingOption> getFcPackagingOptions() {
        return fcPackagingOptions;
    }
}
