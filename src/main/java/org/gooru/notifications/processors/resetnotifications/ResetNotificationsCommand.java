package org.gooru.notifications.processors.resetnotifications;

import org.gooru.notifications.infra.constants.Constants;
import org.gooru.notifications.infra.constants.HttpConstants;
import org.gooru.notifications.infra.data.EventBusMessage;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * @author ashish.
 */

class ResetNotificationsCommand {
  private final UUID userId;
  private final Long notificationId;
  private final String role;

  private static final Logger LOGGER = LoggerFactory.getLogger(ResetNotificationsCommand.class);

  private ResetNotificationsCommand(UUID userId, Long notificationId, String role) {

    this.userId = userId;
    this.notificationId = notificationId;
    this.role = role;
  }

  static ResetNotificationsCommand builder(EventBusMessage eventBusMessage) {
    Long notificationId = validateAndGetNotificationId(eventBusMessage);
    String role = eventBusMessage.getRequestBody().getString(Constants.Params.ROLE_PARAM);
    return new ResetNotificationsCommand(eventBusMessage.getUserId(), notificationId, role);
  }

  private static Long validateAndGetNotificationId(EventBusMessage eventBusMessage) {
    try {
      Long id = eventBusMessage.getRequestBody().getLong(Constants.Params.ID_PARAM);
      if (id == null || id <= 0) {
        throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
            "Invalid notification id");
      }
      return id;
    } catch (ClassCastException e) {
      LOGGER.warn("Invalid notification id");
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST,
          "Invalid notification id");
    }
  }

  UUID getUserId() {
    return userId;
  }

  Long getNotificationId() {
    return notificationId;
  }

  public String getRole() {
    return role;
  }

  boolean isTeacherNotification() {
    return Constants.Params.TEACHER_ROLE_PARAM.equals(role);
  }

  boolean isStudentNotification() {
    return Constants.Params.STUDENT_ROLE_PARAM.equals(role);
  }

  @Override
  public String toString() {
    return "ResetNotificationsCommand{" + "userId=" + userId + ", notificationId=" + notificationId
        + ", role='" + role + '\'' + '}';
  }
}
