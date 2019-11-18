package org.gooru.notifications.routes;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.dropwizard.MetricsService;
import io.vertx.ext.web.Router;
import org.gooru.notifications.infra.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */
class RouteInternalConfigurator implements RouteConfigurator {

  private static final Logger LOGGER = LoggerFactory.getLogger(RouteInternalConfigurator.class);
  private EventBus eb = null;
  private long mbusTimeout;

  @Override
  public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
    LOGGER.debug("Configuring routes for internal route");
    eb = vertx.eventBus();
    mbusTimeout = config.getLong(Constants.EventBus.MBUS_TIMEOUT, 30L) * 1000;
    router.route(Constants.Route.API_INTERNAL_BANNER).handler(routingContext -> {
      JsonObject result =
          new JsonObject().put("Organisation", "gooru.org").put("Product", "notifications")
              .put("purpose", "api").put("mission", "Honor the human right to education");
      routingContext.response().end(result.toString());
    });

    final MetricsService metricsService = MetricsService.create(vertx);
    router.route(Constants.Route.API_INTERNAL_METRICS).handler(routingContext -> {
      JsonObject ebMetrics = metricsService.getMetricsSnapshot(vertx);
      routingContext.response().end(ebMetrics.toString());
    });
  }
}
