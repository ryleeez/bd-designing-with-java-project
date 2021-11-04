package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.atacurriculumsustainabilityshipmentservicelambda.App;
//import com.amazon.atacurriculumsustainabilityshipmentservicelambda.SustainabilityShipmentClientException;

//import com.amazon.ata.test.tct.instrospection.ExecuteTctSuiteHelper;
//import com.amazon.bones.lambdarouter.LambdaRouter;
//import com.amazon.bones.lambdarouter.LambdaRouterSignature;
//import com.amazon.bones.lambdarouter.handlers.ActivityInstantiatorHandler;
//import com.amazon.bones.lambdarouter.handlers.ActivityInvocationHandler;
//import com.amazon.bones.lambdarouter.handlers.ProxyResultSerializerHandler;
//import com.amazon.bones.lambdarouter.handlers.RequestDeserializerHandler;
//import com.amazon.bones.lambdarouter.handlers.RouteMatcherHandler;
//import com.amazon.bones.lambdarouter.handlers.ValidationHandler;
//import com.amazon.bones.lambdarouter.handlers.XRayTraceHandler;
//import com.amazon.bones.util.DataConverter;
//
//import static com.amazon.bones.lambdarouter.LambdaRouterHttpMethod.GET;

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
