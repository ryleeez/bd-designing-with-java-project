package com.amazon.ata.datastore;

import com.amazon.ata.types.*;

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
            createFcPackagingOption("IND1", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10))),
            createFcPackagingOption("ABE2", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20))),
            createFcPackagingOption("ABE2", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(40), BigDecimal.valueOf(40), BigDecimal.valueOf(40))),
            createFcPackagingOption("YOW4", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(10))),
            createFcPackagingOption("YOW4", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20))),
            createFcPackagingOption("YOW4", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(60), BigDecimal.valueOf(60), BigDecimal.valueOf(60))),
            createFcPackagingOption("IAD2", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20))),
            createFcPackagingOption("IAD2", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(20), BigDecimal.valueOf(20), BigDecimal.valueOf(20))),
            createFcPackagingOption("PDX1", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(40), BigDecimal.valueOf(40), BigDecimal.valueOf(40))),
            createFcPackagingOption("PDX1", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(60), BigDecimal.valueOf(60), BigDecimal.valueOf(60))),
            createFcPackagingOption("PDX1", Material.CORRUGATE, new Box(Material.CORRUGATE, BigDecimal.valueOf(60), BigDecimal.valueOf(60), BigDecimal.valueOf(60)))

    );

    /**
     * Create fulfillment center packaging option from provided parameters.
     */
    private FcPackagingOption createFcPackagingOption(String fcCode, Material material, Box box) {
        FulfillmentCenter fc = new FulfillmentCenter(fcCode);
        return new FcPackagingOption(fc, box);
    }


    public List<FcPackagingOption> getFcPackagingOptions() {
        return fcPackagingOptions;
    }
}
