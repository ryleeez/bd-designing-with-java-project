package com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity;

import com.amazon.bones.lambdarouter.LambdaActivity;
import com.amazon.bones.lambdarouter.LambdaProxyInput;
import com.amazon.bones.lambdarouter.RequestContext;
import com.amazon.bones.lambdarouter.handlers.ActivityInstantiatorHandler;
import com.amazon.bones.lambdarouter.handlers.ActivityInvocationHandler;
import com.amazon.bones.lambdarouter.handlers.Chain;
import com.amazon.bones.lambdarouter.handlers.Job;
import com.amazon.bones.lambdarouter.handlers.ProxyResultSerializerHandler;
import com.amazon.bones.lambdarouter.handlers.RequestDeserializerHandler;
import com.amazon.bones.lambdarouter.handlers.RequestHandler;
import com.amazon.bones.lambdarouter.handlers.RouteMatcherHandler;
import com.amazon.bones.lambdarouter.handlers.ValidationHandler;
import com.amazon.bones.lambdarouter.handlers.XRayTraceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.amazon.bones.lambdarouter.LambdaRouterHttpMethod.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ShipmentServiceLambdaRouterTest {

    private ShipmentServiceLambdaRouter router;

    @BeforeEach
    public void setup() {
        router = new ShipmentServiceLambdaRouter();
        router.initialize();

    }

    @Test
    public void getHandlers_forShipmentServiceRouter_containsCorrectHandlers() {
        // GIVEN
        Chain chain = router.getChain();

        // WHEN
        List<RequestHandler> handlers = chain.getHandlers();

        // THEN
        assertEquals(handlers.size(), 7);
        assertHasHandler(handlers, XRayTraceHandler.class);
        assertHasHandler(handlers, ProxyResultSerializerHandler.class);
        assertHasHandler(handlers, RouteMatcherHandler.class);
        assertHasHandler(handlers, ActivityInstantiatorHandler.class);
        assertHasHandler(handlers, RequestDeserializerHandler.class);
        assertHasHandler(handlers, ValidationHandler.class);
        assertHasHandler(handlers, ActivityInvocationHandler.class);
    }

    private static <E> void assertHasHandler(List<? super E> handlers, Class<E> type) {
        if (getHandler(handlers, type) == null) {
            fail("Handler of type: " + type.getSimpleName() + " is missing." );
        }
    }

    private static <E> E getHandler(List<? super E> handlers, Class<E> type) {
        return handlers.stream()
            .filter(type::isInstance)
            .map(type::cast)
            .findAny()
            .orElse(null);
    }

    @ParameterizedTest
    @MethodSource("routeToActivity")
    public void getMatchedActivitySupplier_validRoute_providesValidActivity(String route, Class<?> expectedActivity) {
        // GIVEN
        List<RequestHandler> handlers = router.getChain().getHandlers();
        RouteMatcherHandler routeHandler = getHandler(handlers, RouteMatcherHandler.class);
        Job job = createRouteJob(route);
        // This would throw if the route is not found in the mapping.
        routeHandler.before(job);

        // WHEN
        // Runs the Supplier<LambdaActivity>, verifying that the Supplier does not cause errors.
        LambdaActivity a = job.getMatchedActivitySupplier().get();

        // THEN
        assertEquals(a.getClass(), expectedActivity);
    }

    public static Stream<Arguments> routeToActivity() {
        // Allows for easy addition of new APIs.
        return Stream.of(
            arguments("/shipments", PrepareShipmentActivity.class),
            arguments("/executeTcts", ExecuteTctActivity.class));
    }

    private Job createRouteJob(String route) {
        RequestContext requestContext = RequestContext.builder()
            .httpMethod(GET.name())
            .resourcePath(route)
            .build();

        LambdaProxyInput input = LambdaProxyInput.builder()
            .requestContext(requestContext)
            .build();

        Job job = new Job();
        job.setLambdaProxyInput(input);
        return job;
    }
}
