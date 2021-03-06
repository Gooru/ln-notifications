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
        if (((event.getPathId() == null || event.getPathId() == 0) && event.getPathType() != null) ||
                ((event.getPathId() != null && event.getPathId() != 0) && event.getPathType() == null)) {
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
        private static final String ROUTE0 = "route0";
        private static final String SYSTEM = "system";
        private static final String TEACHER = "teacher";
        private static final List<String> VALID_VALUES = Arrays.asList(SYSTEM, TEACHER, ROUTE0);

        private PathTypeValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return (value == null) || (VALID_VALUES.contains(value));
        }

        static boolean isMainPath(String value) {
            return value == null;
        }

        static boolean isSystemPath(String value) {
            return (value != null && value.equals(SYSTEM));
        }

        static boolean isTeacherPath(String value) {
            return (value != null && value.equals(TEACHER));
        }

        static boolean isRoute0Path(String value) {
            return (value != null && value.equals(ROUTE0));
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
        private static final String INITIATE = "initiate";
        private static final String COMPLETE = "complete";

        private static final List<String> VALID_VALUES = Arrays.asList(INITIATE, COMPLETE);

        private ActionValidator() {
            throw new AssertionError();
        }

        static boolean isValid(String value) {
            return (value != null) && (VALID_VALUES.contains(value));
        }

        static boolean isActionInitiate(String value) {
            return INITIATE.equals(value);
        }

        static boolean isActionComplete(String value) {
            return COMPLETE.equals(value);
        }
    }

}
