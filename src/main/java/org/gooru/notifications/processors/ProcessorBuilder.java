package org.gooru.notifications.processors;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.gooru.notifications.processors.resetnotifications.ResetNotificationsProcessor;
import org.gooru.notifications.processors.studentnotifications.FetchStudentNotificationsProcessor;
import org.gooru.notifications.processors.teachernotifications.FetchTeacherNotificationsProcessor;
import org.gooru.notifications.responses.MessageResponse;

/**
 * @author ashish.
 */
public final class ProcessorBuilder {

    public static AsyncMessageProcessor buildPlaceHolderExceptionProcessor(Vertx vertx, Message<JsonObject> message) {
        return () -> {
            Future<MessageResponse> future = Future.future();
            future.fail(new IllegalStateException("Illegal State for processing command"));
            return future;
        };
    }

    public static AsyncMessageProcessor buildFetchTeacherNotificationsProcessor(Vertx vertx,
        Message<JsonObject> message) {
        return new FetchTeacherNotificationsProcessor(vertx, message);
    }

    public static AsyncMessageProcessor buildFetchStudentNotificationsProcessor(Vertx vertx,
        Message<JsonObject> message) {
        return new FetchStudentNotificationsProcessor(vertx, message);
    }

    public static AsyncMessageProcessor buildResetNotificationProcessor(Vertx vertx, Message<JsonObject> message) {
        return new ResetNotificationsProcessor(vertx, message);
    }
}
