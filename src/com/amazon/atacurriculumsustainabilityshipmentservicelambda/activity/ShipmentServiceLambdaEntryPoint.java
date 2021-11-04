package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

// import com.amazon.bones.lambdarouter.LambdaEntryPoint;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the Shipment Service functionality.
 *
 * This is the entry point of the Lambda function. Each call will create a new instance.
 */
//public class ShipmentServiceLambdaEntryPoint extends LambdaEntryPoint<ShipmentServiceLambdaRouter> {
public class ShipmentServiceLambdaEntryPoint implements RequestHandler<SQSEvent, String> {
    @Override
    public String handleRequest(SQSEvent input, Context context) {
        return "ShipmentServiceLambdaEntryPoint Ended";
    }

/**
 * Creates new ShipmentServiceLambdaEntryPoint.
 */
//    public ShipmentServiceLambdaEntryPoint() {
//        super(ShipmentServiceLambdaRouter.class);
//    }
}
