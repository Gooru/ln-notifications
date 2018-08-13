package org.gooru.notifications.writers;

import org.gooru.notifications.infra.utils.UuidUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author ashish.
 */

final class NotificationsEventValidator {

    private NotificationsEventValidator() {
        throw new AssertionError();
    }

    static void validateEvent(NotificationsEvent event) {
        if (event.getNotificationType() == null || event.getNotificationType().isEmpty() ||
                !NotificationTypeValidator.isValid(event.getNotificationType())) {
            throw new IllegalArgumentException("Invalid notification type: " + event.getNotificationType());
        }
        if (!UuidUtils.validateUuid(event.getUserId())) {
            throw new IllegalArgumentException("Invalid user id: " + event.getUserId());
        }
        if (!UuidUtils.validateUuid(event.getClassId())) {
            throw new IllegalArgumentException("Invalid class id: " + event.getClassId());
        }
        if (!UuidUtils.validateUuid(event.getCourseId())) {
            throw new IllegalArgumentException("Invalid course id: " + event.getCourseId());
        }
        if (!UuidUtils.validateUuid(event.getUnitId())) {
            throw new IllegalArgumentException("Invalid unit id: " + event.getUnitId());
        }
        if (!UuidUtils.validateUuid(event.getLessonId())) {
            throw new IllegalArgumentException("Invalid lesson id: " + event.getLessonId());
        }
        if (!UuidUtils.validateUuidAllowNull(event.getCollectionId())) {
            throw new IllegalArgumentException("Invalid collection id: " + event.getCollectionId());
        }
        if (!UuidUtils.validateUuid(event.getCurrentItemId())) {
            throw new IllegalArgumentException("Invalid current item id: " + event.getCurrentItemId());
        }
        if ((event.getPathId() == null && event.getPathType() != null) ||
                (event.getPathId() != null && event.getPathType() == null)) {
            throw new IllegalArgumentException("Both path id and path type should be valid or null");
        }
        if (!PathTypeValidator.isValid(event.getPathType())) {
            throw new IllegalArgumentException("Invalid path type: " + event.getPathType());
        }
        if (!CurrentItemTypeValidator.isValid(event.getCurrentItemType())) {
            throw new IllegalArgumentException("Invalid current item type: " + event.getCurrentItemType());
        }
        if (event.getAction() == null || event.getAction().isEmpty() || !ActionValidator.isValid(event.getAction())) {
            throw new IllegalArgumentException("Invalid action: " + event.getAction());
        }
    }

    static final class CurrentItemTypeValidator {

        private static final List<String> VALID_VALUES =
            Arrays.asList("collection", "assessment", "collection-external", "assessment-external");

        private CurrentItemTypeValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return ((value != null) && VALID_VALUES.contains(value));
        }
    }

    static final class PathTypeValidator {
        private static final List<String> VALID_VALUES = Arrays.asList("system", "teacher", "route0");

        private PathTypeValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return (value == null) || (VALID_VALUES.contains(value));
        }
    }

    static final class NotificationTypeValidator {
        private static final List<String> VALID_VALUES = Arrays.asList("teacher.override", "teacher.suggestion",
            "teacher.grading.complete", "student.self.report", "student.gradable.submission");

        private NotificationTypeValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return (value == null) || (VALID_VALUES.contains(value));
        }
    }

    static final class ActionValidator {
        private static final List<String> VALID_VALUES = Arrays.asList("initiate", "complete");

        private ActionValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return (value == null) || (VALID_VALUES.contains(value));
        }
    }

}