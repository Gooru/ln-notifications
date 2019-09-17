package org.gooru.notifications.processors.studentnotifications;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.notifications.infra.data.EventBusMessage;
import org.gooru.notifications.infra.jdbi.DBICreator;
import org.gooru.notifications.processors.AsyncMessageProcessor;
import org.gooru.notifications.responses.MessageResponse;
import org.gooru.notifications.responses.MessageResponseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */
public class FetchStudentNotificationsProcessor implements AsyncMessageProcessor {
  private final Vertx vertx;
  private final Message<JsonObject> message;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(FetchStudentNotificationsProcessor.class);
  private final Future<MessageResponse> result;
  private EventBusMessage eventBusMessage;
  private FetchStudentNotificationsService fetchStudentNotificationsService =
      new FetchStudentNotificationsService(DBICreator.getDbiForDefaultDS());

  public FetchStudentNotificationsProcessor(Vertx vertx, Message<JsonObject> message) {
    this.vertx = vertx;
    this.message = message;
    this.result = Future.future();
  }

  @Override
  public Future<MessageResponse> process() {
    vertx.<MessageResponse>executeBlocking(future -> {
      try {
        this.eventBusMessage = EventBusMessage.eventBusMessageBuilder(message);

        FetchStudentNotificationsCommand command =
            FetchStudentNotificationsCommand.builder(eventBusMessage);
        FetchStudentNotificationsResponse response =
            fetchStudentNotificationsService.fetch(command);
        JsonObject jsonResponse = JsonObject.mapFrom(response);
        future.complete(createResponse(jsonResponse));
      } catch (Throwable throwable) {
        LOGGER.warn("Encountered exception", throwable);
        future.fail(throwable);
      }
    }, asyncResult -> {
      if (asyncResult.succeeded()) {
        result.complete(asyncResult.result());
      } else {
        result.fail(asyncResult.cause());
      }
    });
    return result;
  }

  private MessageResponse createResponse(JsonObject response) {
    return MessageResponseFactory.createOkayResponse(response);
  }

}
