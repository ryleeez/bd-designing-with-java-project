package com.amazon.ata.types;

import java.util.Objects;

/**
 * Represents a pairing between a packaging option and a fulfillment center that supports that packaging option.
 */
public class FcPackagingOption {

    /**
     * The fulfillment center we are providing packaging information about.
     */
    private FulfillmentCenter fulfillmentCenter;

    /**
     * A packaging that is available at the fulfillment center.
     */
    private Packaging packaging;


    /**
     * Instantiates a new FcPackagingOption object.
     * @param fulfillmentCenter - the FC where the packaging Option is available
     * @param packaging - the packaging option available at the provided FC
     */
    public FcPackagingOption(FulfillmentCenter fulfillmentCenter, Packaging packaging) {
        this.fulfillmentCenter = fulfillmentCenter;
        this.packaging = packaging;
    }

    public FulfillmentCenter getFulfillmentCenter() {
        return fulfillmentCenter;
    }

    public Packaging getPackaging() {
        return packaging;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FcPackagingOption that = (FcPackagingOption) o;
        return Objects.equals(fulfillmentCenter.getFcCode(), that.fulfillmentCenter.getFcCode()) &&
                Objects.equals(packaging.getMass(), that.packaging.getMass()) &&
                Objects.equals(packaging.getMaterial(), that.packaging.getMaterial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fulfillmentCenter.getFcCode(), packaging.getMass(), packaging.getMaterial());
    }
}
