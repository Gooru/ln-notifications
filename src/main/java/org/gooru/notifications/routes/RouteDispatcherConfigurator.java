package org.gooru.notifications.routes;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.gooru.notifications.infra.constants.Constants;
import org.gooru.notifications.infra.constants.HttpConstants;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;
import org.gooru.notifications.routes.utils.DeliveryOptionsBuilder;
import org.gooru.notifications.routes.utils.RouteRequestUtility;
import org.gooru.notifications.routes.utils.RouteResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteDispatcherConfigurator implements RouteConfigurator {

  private EventBus eb = null;
  private long mbusTimeout;
  private static final Logger LOGGER = LoggerFactory.getLogger(RouteDispatcherConfigurator.class);

  @Override
  public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
    eb = vertx.eventBus();
    mbusTimeout = config.getLong(Constants.EventBus.MBUS_TIMEOUT, 30L) * 1000;
    router.get(Constants.Route.API_NOTIFICATION_FETCH_TEACHER)
        .handler(this::fetchTeacherNotifications);
    router.get(Constants.Route.API_NOTIFICATION_FETCH_STUDENT)
        .handler(this::fetchStudentSuggestions);
    router.delete(Constants.Route.API_NOTIFICATION_STUDENT_RESET)
        .handler(this::deleteNotificationStudent);
    router.delete(Constants.Route.API_NOTIFICATION_TEACHER_RESET)
        .handler(this::deleteNotificationTeacher);
  }

  private void deleteNotificationStudent(RoutingContext routingContext) {
    deleteNotification(routingContext, Constants.Params.STUDENT_ROLE_PARAM);
  }

  private void deleteNotificationTeacher(RoutingContext routingContext) {
    deleteNotification(routingContext, Constants.Params.TEACHER_ROLE_PARAM);
  }

  private void deleteNotification(RoutingContext routingContext, String role) {
    DeliveryOptions options =
        DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_NOTIFICATIONS_RESET);
    Long id = validateAndGetLong(routingContext.request().getParam(Constants.Params.ID_PARAM));
    JsonObject additionalRequestBody =
        new JsonObject().put(Constants.Params.ID_PARAM, id).put(Constants.Params.ROLE_PARAM, role);
    eb.<JsonObject>send(Constants.EventBus.MBEP_NOTIFICATIONS_DISPATCHER,
        RouteRequestUtility.getBodyForMessage(routingContext, additionalRequestBody), options,
        reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
  }

  private Long validateAndGetLong(String id) {
    if (id != null && !id.isEmpty()) {
      try {
        return Long.valueOf(id);
      } catch (NumberFormatException e) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "Invalid notification id");
      }
    }
    throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
        "Invalid notification id");
  }

  private void fetchStudentSuggestions(RoutingContext routingContext) {
    DeliveryOptions options =
        DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_NOTIFICATIONS_STUDENT);
    eb.<JsonObject>send(Constants.EventBus.MBEP_NOTIFICATIONS_DISPATCHER,
        RouteRequestUtility.getBodyForMessage(routingContext), options,
        reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
  }

  private void fetchTeacherNotifications(RoutingContext routingContext) {
    DeliveryOptions options =
        DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout)
            .addHeader(Constants.Message.MSG_OP, Constants.Message.MSG_OP_NOTIFICATIONS_TEACHER);
    eb.<JsonObject>send(Constants.EventBus.MBEP_NOTIFICATIONS_DISPATCHER,
        RouteRequestUtility.getBodyForMessage(routingContext), options,
        reply -> RouteResponseUtility.responseHandler(routingContext, reply, LOGGER));
  }

}
