package org.gooru.notifications.infra.constants;

import java.util.UUID;

/**
 * @author ashish.
 */
public final class Constants {

  public static final class EventBus {

    public static final String MBEP_AUTH = "org.gooru.notifications.eventbus.auth";
    public static final String MBEP_NOTIFICATIONS_DISPATCHER =
        "org.gooru.notifications.eventbus.dispatcher";

    public static final String MBUS_TIMEOUT = "event.bus.send.timeout.seconds";

    private EventBus() {
      throw new AssertionError();
    }
  }

  public static final class Message {

    public static final String MSG_OP = "mb.operation";
    public static final String MSG_API_VERSION = "api.version";
    public static final String MSG_SESSION_TOKEN = "session.token";
    public static final String MSG_OP_AUTH = "auth";
    public static final String MSG_OP_NOTIFICATIONS_TEACHER = "op.notifications.teacher";
    public static final String MSG_OP_NOTIFICATIONS_STUDENT = "op.notifications.student";
    public static final String MSG_OP_NOTIFICATIONS_RESET = "op.notifications.reset";
    public static final String MSG_KEY_SESSION = "session";
    public static final String MSG_OP_STATUS = "mb.op.status";
    public static final String MSG_OP_STATUS_SUCCESS = "mb.op.status.success";
    public static final String MSG_OP_STATUS_FAIL = "mb.op.status.fail";
    public static final String MSG_USER_ANONYMOUS = "anonymous";
    public static final String MSG_USER_ID = "user_id";
    public static final String MSG_HTTP_STATUS = "http.status";
    public static final String MSG_HTTP_BODY = "http.body";
    public static final String MSG_HTTP_HEADERS = "http.headers";

    public static final String MSG_MESSAGE = "message";
    public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";

    private Message() {
      throw new AssertionError();
    }
  }

  public static final class Response {

    private Response() {
      throw new AssertionError();
    }
  }

  public static final class Params {

    private Params() {
      throw new AssertionError();
    }

    public static final String ID_PARAM = "id";
    public static final String ROLE_PARAM = "role";
    public static final String TEACHER_ROLE_PARAM = "teacher";
    public static final String STUDENT_ROLE_PARAM = "student";
  }

  public static final class Route {

    public static final String API_AUTH_ROUTE = "/api/notifications/*";

    private static final String API_BASE_ROUTE = "/api/notifications/:version/";
    public static final String API_NOTIFICATION_FETCH_TEACHER = API_BASE_ROUTE + "teacher";
    public static final String API_NOTIFICATION_FETCH_STUDENT = API_BASE_ROUTE + "student";
    public static final String API_NOTIFICATION_TEACHER_RESET =
        API_BASE_ROUTE + "teacher/:" + Params.ID_PARAM;
    public static final String API_NOTIFICATION_STUDENT_RESET =
        API_BASE_ROUTE + "student/:" + Params.ID_PARAM;
    public static final String API_INTERNAL_BANNER = "/api/internal/banner";
    public static final String API_INTERNAL_METRICS = "/api/internal/metrics";

    private Route() {
      throw new AssertionError();
    }
  }

  public static final class Misc {

    public static final UUID CLASS_PLACEHOLDER = new UUID(0, 0);
    public static final UUID USER_PLACEHOLDER = new UUID(0, 0);

    private Misc() {
      throw new AssertionError();
    }
  }

  private Constants() {
    throw new AssertionError();
  }
}
