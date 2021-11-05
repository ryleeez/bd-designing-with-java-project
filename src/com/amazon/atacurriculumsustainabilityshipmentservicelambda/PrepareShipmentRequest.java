package com.amazon.atacurriculumsustainabilityshipmentservicelambda;

/****************************************************************************************************
 * Created by Frank to replace  the missing PrepareShipmentRequest class from ATA
 ***************************************************************************************************/

import java.math.BigDecimal;
import java.util.Objects;

public class PrepareShipmentRequest {

    /**
     * Represents a request sent to app for packaging information.
     */

    /****************************************************************************************************
    * Member variables
    ***************************************************************************************************/
        /**
         * The item's unique identifier.
         */
        private String asin;   // Amazon Standard Identification Number - 10 alphanumeric characters (Frank)

        /**
         * A description of the item.
         */
        private String description;

        /**
         * The length of the item, in centimeters.
         */
        private BigDecimal length;

        /**
         * The shortest dimension of the item, in centimeters.
         */
        private BigDecimal width;

        /**
         * The longest dimension of the item, in centimeters.
         */
        private BigDecimal height;

        /**
         * The unique identifier code for a fulfillment center.
        */
        private String fcCode;    // 4-chars where first 3 are airport code for nearest city (Frank)

    /****************************************************************************************************
     * constructor(s)
     ***************************************************************************************************/
    public PrepareShipmentRequest() { // Frank - not sure default ctor is needed or not.  Added just in case
        this.asin   = "0000000000";
        this.description = "Frank Test Default Item - should not ever be needed - only used for testing";
        this.length = new BigDecimal(0);
        this.width  = new BigDecimal(0);
        this.height = new BigDecimal(0);
        this.fcCode = "IND1";  // Frank - Indianapolis - chosen because it is the first one in our datastore
        }


    public PrepareShipmentRequest(String asin, String description, BigDecimal length, BigDecimal width, BigDecimal height) {
        this.asin = asin;
        this.description = description;
        this.length = length;
        this.width = width;
        this.height = height;
    }
    /****************************************************************************************************
     * getters/ setters
     ***************************************************************************************************/
    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public String getFcCode() {
        return fcCode;
    }

    public void setFcCode(String fcCode) {
        this.fcCode = fcCode;
    }

    /****************************************************************************************************
     * auxillary methods
     ***************************************************************************************************/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrepareShipmentRequest that = (PrepareShipmentRequest) o;
        return getAsin().equals(that.getAsin()) && getDescription().equals(that.getDescription()) && getLength().equals(that.getLength()) && getWidth().equals(that.getWidth()) && getHeight().equals(that.getHeight()) && getFcCode().equals(that.getFcCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAsin(), getDescription(), getLength(), getWidth(), getHeight(), getFcCode());
    }

    @Override
    public String toString() {
        return "PrepareShipmentRequest{" +
                "asin='" + asin + '\'' +
                ", description='" + description + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", fcCode='" + fcCode + '\'' +
                '}';
    }
}
