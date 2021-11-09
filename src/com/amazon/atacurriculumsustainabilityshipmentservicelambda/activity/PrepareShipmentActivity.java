package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentRequest;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.PrepareShipmentResponse;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CarbonCostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.cost.CostStrategy;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao.PackagingDAO;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.datastore.PackagingDatastore;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.service.ShipmentService;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.FulfillmentCenter;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.Item;
import com.amazon.atacurriculumsustainabilityshipmentservicelambda.types.ShipmentOption;

//import com.amazon.bones.lambdarouter.LambdaActivityBase;
//import com.amazon.bones.util.DataConverter;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the Shipment Service functionality.
 *
 * This is implementation of the PrepareShipment activity. It handles PrepareShipment requests by returning
 * the appropriate shipment option.
 */
public class PrepareShipmentActivity //  extends LambdaActivityBase<PrepareShipmentRequest, PrepareShipmentResponse>
                      implements RequestHandler<PrepareShipmentRequest, String> {

    // Frank - Objects defined to get the code to work - used when defining ShipmentService
    private PackagingDatastore dataStore = new PackagingDatastore();
    private PackagingDAO packagingDAO = new PackagingDAO(dataStore);
    private CostStrategy costStrategy = new CarbonCostStrategy();

    /**
     * Shipment service used to retrieve shipment options.
     */
    //      ShipmentService(PackagingDAO packagingDAO, CostStrategy costStrategy)
    private ShipmentService shipmentService = new ShipmentService(packagingDAO, costStrategy);
    /**
     * Data converter used to create shipment option JSON that is returned in the response.
     */
    //private DataConverter converter;  // DataConverter is defined in bones

    public PrepareShipmentActivity() {

    }


    /**
     * Instantiates a new PrepareShipmentActivity object.
     * @param shipmentService Shipment service used to retrieve shipment options.
     * @param converter Data converter used to create shipment option JSON that is returned in the response.
     */
    //public PrepareShipmentActivity(ShipmentService shipmentService, DataConverter converter) {
     public PrepareShipmentActivity(ShipmentService shipmentService) {
        //super(PrepareShipmentRequest.class);
        this.shipmentService = shipmentService;
       // this.converter = converter;
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
    // public PrepareShipmentResponse handleRequest(PrepareShipmentRequest request) throws Exception {
    // Not sure what/where PrepareShipmentResponse is defined or does
    //     public String handleRequest(SQSEvent input, Context context) {
         public String handleRequest(PrepareShipmentRequest request, Context context) {
    //   public PrepareShipmentResponse handleRequest(PrepareShipmentRequest request, Context context) {

        // Frank - new request for testing
        PrepareShipmentRequest testRequest = new PrepareShipmentRequest();

        System.out.println("Processing request: " + request);

        Item item = Item.builder()    // Test item defined with constants (Frank)
                .withAsin(request.getAsin())
                .withDescription(request.getDescription())
                .withLength(request.getLength())
                .withWidth(request.getWidth())
                .withHeight(request.getHeight())
                .build();

        System.out.println("Processing with Item: " + item);

// **** Start of Franks hardcoded test data ****
//        Item item = Item.builder()    // Test item defined with constants (Frank)
//            .withAsin("Frank31952")
//            .withDescription("Frank Test Item")
//            // Need to find a way to handle BigDecimal validation - currently there is a problem with unmarshalling
//            // of GET requests that contain BigDecimal values as defined in the Coral model.
//            .withLength(new BigDecimal(10))
//            .withWidth(new BigDecimal(8))
//            .withHeight(new BigDecimal(4))
//            .build();
// **** End of Franks hardcoded test data ****


        FulfillmentCenter fulfillmentCenter = new FulfillmentCenter(request.getFcCode());   // Phoenix Fullfilment Center #3 (Frank)

        ShipmentOption shipmentOption = shipmentService.findShipmentOption(item, fulfillmentCenter);
        System.out.println("shipmentOption: " + shipmentOption);
        if (shipmentOption == null) return null;

        // Converting the shipmentOption to JSON to simplify inheritance related issues
        //String shipmentOptionJSON = converter.toJson(shipmentOption);

        // Code added by Frank to replace converter call above to generate json from shipmentOption
        ObjectMapper objectMapper = new ObjectMapper();
        String shipmentOptionJSON = "wtf";
        try {
            shipmentOptionJSON = objectMapper.writeValueAsString(shipmentOption);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

// return commented out due to not knowing what PrepareShipmentResponse does
//        return PrepareShipmentResponse.builder()
//            .withAttributes(shipmentOptionJSON)
//            .build();
        System.out.println("Returning: " + shipmentOptionJSON);
        return shipmentOptionJSON;
    }
}

