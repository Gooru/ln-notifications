package org.gooru.notifications.routes.utils;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.gooru.notifications.infra.constants.Constants;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ashish
 */
public final class RouteRequestUtility {
    private RouteRequestUtility() {
        throw new AssertionError();
    }

    /*
     * If the incoming request is POST or PUT, it is expected to have a payload
     * of JSON which is returned. In case of GET request, any query parameters
     * will be used to create a JSON body. Note that only query string is used
     * and not path matchers. In case of no query parameters send out empty Json
     * object, but don't send null
     */

    public static JsonObject getBodyForMessage(RoutingContext routingContext, JsonObject toBeMergedRequestBody) {
        JsonObject httpBody, result = new JsonObject();
        if (routingContext.request().method().name().equals(HttpMethod.POST.name()) || routingContext.request().method()
            .name().equals(HttpMethod.PUT.name())) {
            httpBody = routingContext.getBodyAsJson();
        } else if (Objects.equals(routingContext.request().method().name(), HttpMethod.GET.name())) {
            httpBody = new JsonObject();
            String uri = routingContext.request().query();
            if (uri != null) {
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri, false);
                Map<String, List<String>> prms = queryStringDecoder.parameters();
                if (!prms.isEmpty()) {
                    for (Map.Entry<String, List<String>> entry : prms.entrySet()) {
                        List<String> values = entry.getValue();
                        if (values != null) {
                            // We do not allow repetition of params and always use first one
                            httpBody.put(entry.getKey(), entry.getValue().get(0));
                        }
                    }
                }
            }
        } else {
            httpBody = new JsonObject();
        }

        if (toBeMergedRequestBody != null && !toBeMergedRequestBody.isEmpty()) {
            httpBody.mergeIn(toBeMergedRequestBody);
        }

        result.put(Constants.Message.MSG_HTTP_BODY, httpBody);
        result
            .put(Constants.Message.MSG_KEY_SESSION, (JsonObject) routingContext.get(Constants.Message.MSG_KEY_SESSION));
        result.put(Constants.Message.MSG_USER_ID, (String) routingContext.get(Constants.Message.MSG_USER_ID));
        result
            .put(Constants.Message.MSG_SESSION_TOKEN, (String) routingContext.get(Constants.Message.MSG_SESSION_TOKEN));
        return result;
    }

    public static JsonObject getBodyForMessage(RoutingContext routingContext) {
        return getBodyForMessage(routingContext, null);
    }
}
