package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

/**
 * PARTICIPANTS: You are not expected to modify or use this class directly. Please do not modify the code contained
 * in this class as doing so might break the Shipment Service functionality.
 *
 * Router class is responsible for handling incoming request and routing them to the correct activity class.
 */
public class ShipmentServiceLambdaRouter /* extends LambdaRouter */ {

//    /**
//     * Default constructor.
//     */
//    public ShipmentServiceLambdaRouter() {
//    }
//
//    /**
//     * This method is used to initialize the request processing chain which is then
//     * used to determine which activity will handle the incoming requests.
//     */
//    //@Override
//    public void initialize() {
//        DataConverter dataConverter = getDataConverter();
//
//        getChain().setHandlers(
//                new XRayTraceHandler("ATACurriculumSustainabilityShipmentServiceLambda"),
//                new ProxyResultSerializerHandler(dataConverter),
//                new RouteMatcherHandler()
//                        .withRoute(new LambdaRouterSignature(GET, "/shipments"),
//                            () -> new PrepareShipmentActivity(App.getShipmentService(), dataConverter))
//                        // PARTICIPANTS: The route below should NOT be touched when working on the project!
//                        .withRoute(new LambdaRouterSignature(GET, "/executeTcts"),
//                            () -> new ExecuteTctActivity(new ExecuteTctSuiteHelper())),
//                new ActivityInstantiatorHandler(),
//                new RequestDeserializerHandler(dataConverter),
//                // Validate API inputs
//                new ValidationHandler(SustainabilityShipmentClientException.class),
//                new ActivityInvocationHandler());
//    }
//
}
