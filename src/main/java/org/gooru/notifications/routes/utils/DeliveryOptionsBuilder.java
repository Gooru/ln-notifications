package org.gooru.notifications.routes.utils;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.ext.web.RoutingContext;
import org.gooru.notifications.infra.constants.Constants;

/**
 * @author ashish
 */
public final class DeliveryOptionsBuilder {
    private DeliveryOptionsBuilder() {
        throw new AssertionError();
    }

    public static DeliveryOptions buildWithApiVersion(RoutingContext context) {
        final String apiVersion = context.request().getParam("version");
        VersionValidatorUtility.validateVersion(apiVersion);
        return new DeliveryOptions().addHeader(Constants.Message.MSG_API_VERSION, apiVersion);
    }

    public static DeliveryOptions buildWithoutApiVersion(RoutingContext context, long timeout, String op) {
        return new DeliveryOptions().setSendTimeout(timeout * 1000).addHeader(Constants.Message.MSG_OP, op);
    }

    public static DeliveryOptions buildWithoutApiVersion(long timeout) {
        return new DeliveryOptions().setSendTimeout(timeout * 1000);
    }
}
