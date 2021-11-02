package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentRequest;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentResponse;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.service.ShipmentService;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

import com.amazon.bones.lambdarouter.LambdaActivityBase;
import com.amazon.bones.util.DataConverter;

import java.math.BigDecimal;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the Shipment Service functionality.
 *
 * This is implementation of the PrepareShipment activity. It handles PrepareShipment requests by returning
 * the appropriate shipment option.
 */
public class PrepareShipmentActivity extends LambdaActivityBase<PrepareShipmentRequest, PrepareShipmentResponse> {
    /**
     * Shipment service used to retrieve shipment options.
     */
    private ShipmentService shipmentService;
    /**
     * Data converter used to create shipment option JSON that is returned in the response.
     */
    private DataConverter converter;

    /**
     * Instantiates a new PrepareShipmentActivity object.
     * @param shipmentService Shipment service used to retrieve shipment options.
     * @param converter Data converter used to create shipment option JSON that is returned in the response.
     */
    public PrepareShipmentActivity(ShipmentService shipmentService, DataConverter converter) {
        super(PrepareShipmentRequest.class);
        this.shipmentService = shipmentService;
        this.converter = converter;
    }

    /**
     * This method handles the incoming request by calling the shipment service and returning the
     * appropriate shipment option for the fulfillment center and item provided in the request.
     *
     * @param request contains information on fulfillment center and item
     * @return response that contains appropriate shipment option that can be used to pack the provided item
     * @throws Exception if the request can't be fulfilled
     */
    @Override
    public PrepareShipmentResponse handleRequest(PrepareShipmentRequest request) throws Exception {
        Item item = Item.builder()
            .withAsin(request.getItemAsin())
            .withDescription(request.getItemDescription())
            // Need to find a way to handle BigDecimal validation - currently there is a problem with unmarshalling
            // of GET requests that contain BigDecimal values as defined in the Coral model.
            .withLength(new BigDecimal(request.getItemLength()))
            .withWidth(new BigDecimal(request.getItemWidth()))
            .withHeight(new BigDecimal(request.getItemHeight()))
            .build();

        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(request.getFcCode());

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(item, fulfillmentCenter);

        // Converting the shipmentOption to JSON to simplify inheritance related issues
        String shipmentOptionJSON = converter.toJson(shipmentOption);

        return PrepareShipmentResponse.builder()
            .withAttributes(shipmentOptionJSON)
            .build();
    }
}

