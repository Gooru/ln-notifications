package org.gooru.notifications.processors.resetnotifications;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Deletes a notification.
 * For student, notification is deleted if provided notification id belongs to the user. In case, it does not,
 * silently ignore the operation.
 * For teachers, a check is made to make sure that class id for the specified notification belongs to user as teacher
 * or co-teacher. In case, notification does not exists, or user is not authorized, we silently ignore the operation.
 * NOTE: In case of non existence, or no authorization, this implementation silently ignores operation.
 * NOTE: Only non actionable notifications can be deleted/reset using this service
 *
 * @author ashish.
 */

class ResetNotificationsService {

    private final DBI dbi;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResetNotificationsService.class);
    private ResetNotificationsCommand command;
    private ResetNotificationsDao dao;

    ResetNotificationsService(DBI dbi) {
        this.dbi = dbi;
    }

    void reset(ResetNotificationsCommand command) {
        this.command = command;
        LOGGER.info("Will process notification reset for: {}", command.toString());
        if (notificationCanBeReset()) {
            if (command.isStudentNotification()) {
                resetStudentNotification();
            } else if (command.isTeacherNotification()) {
                resetTeacherNotification();
            } else {
                LOGGER.warn("Invalid notification type: {}", command.getRole());
            }
        } else {
            LOGGER.warn("User '{}' Tried to reset actionable/deleted notification: '{}'", command.getUserId(),
                    command.getNotificationId());
        }
    }

    private boolean notificationCanBeReset() {
        // Notification can be reset only if it is non actionable
        Boolean actionable = true;
        if (command.isStudentNotification()) {
            actionable = getDao().isStudentNotificationActionable(command.getNotificationId());
        } else if (command.isTeacherNotification()) {
            actionable = getDao().isTeacherNotificationActionable(command.getNotificationId());
        } else {
            LOGGER.warn("Invalid notification type: {}", command.getRole());
        }
        if (actionable == null) {
            // DB field is not null. If we get it from DB then it must be true/false. Else it does not exist
            LOGGER.warn("User: '{}' tried to delete already deleted notification: '{}'.", command.getUserId(),
                command.getNotificationId());
            return false;
        }
        return !actionable;
    }

    private void resetStudentNotification() {
        // Delete notification based on user id and notification id
        getDao().deleteStudentNotification(command.getNotificationId(), command.getUserId());
    }

    private void resetTeacherNotification() {
        // Fetch the class id for notification
        UUID classId = getDao().fetchClassIdForTeacherNotification(command.getNotificationId());
        if (classId != null) {
            // Validate that user is teacher or collaborator for that class
            if (getDao().checkUserIsAuthorizedAsTeacherForClass(classId, command.getUserId())) {
                // If yes, delete the whole notification (don't decrement count)
                getDao().deleteTeacherNotification(command.getNotificationId());
            } else {
                LOGGER.warn("The user '{}' is not authorized to delete notification: '{}'.", command.getUserId(),
                    command.getNotificationId());
            }
        } else {
            LOGGER.warn("The notification '{}' does not exist. ", command.getNotificationId());
        }
    }

    private ResetNotificationsDao getDao() {
        if (dao == null) {
            dao = dbi.onDemand(ResetNotificationsDao.class);
        }
        return dao;
    }
}
