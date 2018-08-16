package org.gooru.notifications.writers;

import io.vertx.core.json.JsonObject;
import org.gooru.notifications.infra.utils.UuidUtils;

import java.util.UUID;

/**
 * @author ashish.
 */

class NotificationsConsumerCommand {
    private UUID userId;
    private UUID classId;
    private UUID courseId;
    private UUID unitId;
    private UUID lessonId;
    private UUID collectionId;
    private UUID currentItemId;
    private String currentItemType;
    private String pathType;
    private Long pathId;
    private String notificationType;
    private String action;

    static NotificationsConsumerCommand build(String input) {
        JsonObject json = new JsonObject(input);
        NotificationsEvent event = json.mapTo(NotificationsEvent.class);
        NotificationsEventValidator.validateEvent(event);
        return createCommandFromEvent(event);
    }

    private static NotificationsConsumerCommand createCommandFromEvent(NotificationsEvent event) {
        NotificationsConsumerCommand command = new NotificationsConsumerCommand();
        command.userId = UuidUtils.convertStringToUuid(event.getUserId());
        command.classId = UuidUtils.convertStringToUuid(event.getClassId());
        command.courseId = UuidUtils.convertStringToUuid(event.getCourseId());
        command.unitId = UuidUtils.convertStringToUuid(event.getUnitId());
        command.lessonId = UuidUtils.convertStringToUuid(event.getLessonId());
        command.collectionId = UuidUtils.convertStringToUuid(event.getCollectionId());
        command.currentItemId = UuidUtils.convertStringToUuid(event.getCurrentItemId());
        command.currentItemType = event.getCurrentItemType();
        command.pathId = event.getPathId();
        command.pathType = event.getPathType();
        command.notificationType = event.getNotificationType();
        command.action = event.getAction();
        return command;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getClassId() {
        return classId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public UUID getLessonId() {
        return lessonId;
    }

    public UUID getCollectionId() {
        return collectionId;
    }

    public UUID getCurrentItemId() {
        return currentItemId;
    }

    public String getCurrentItemType() {
        return currentItemType;
    }

    public String getPathType() {
        return pathType;
    }

    public Long getPathId() {
        return pathId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getAction() {
        return action;
    }

    public void setCollectionId(UUID collectionId) {
        // This code is to enable hack so that we can set collection id later. But we do this only if we are on
        // system path.
        if (isOnSystemPath()) {
            this.collectionId = collectionId;
        } else {
            throw new IllegalStateException("Trying to set collection id in command when it is not on system path");
        }
    }

    public boolean isActionComplete() {
        return NotificationsEventValidator.ActionValidator.isActionComplete(action);
    }

    public boolean isActionInitiate() {
        return NotificationsEventValidator.ActionValidator.isActionInitiate(action);
    }

    public boolean isOnMainPath() {
        return NotificationsEventValidator.PathTypeValidator.isMainPath(pathType);
    }

    public boolean isOnSystemPath() {
        return NotificationsEventValidator.PathTypeValidator.isSystemPath(pathType);
    }

    public boolean isOnTeacherPath() {
        return NotificationsEventValidator.PathTypeValidator.isTeacherPath(pathType);
    }

    public boolean isOnRoute0() {
        return NotificationsEventValidator.PathTypeValidator.isRoute0Path(pathType);
    }

    @Override
    public String toString() {
        return "NotificationsConsumerCommand{" + "userId=" + userId + ", classId=" + classId + ", courseId=" +
                   courseId + ", unitId=" + unitId + ", lessonId=" + lessonId + ", collectionId=" + collectionId +
                   ", currentItemId=" + currentItemId + ", currentItemType='" + currentItemType + '\'' +
                   ", pathType='" + pathType + '\'' + ", pathId=" + pathId + ", notificationType='" + notificationType +
                   '\'' + ", action='" + action + '\'' + '}';
    }

    public NotificationsConsumerCommandBean asBean() {
        NotificationsConsumerCommandBean bean = new NotificationsConsumerCommandBean();
        bean.userId = userId;
        bean.classId = classId;
        bean.courseId = courseId;
        bean.unitId = unitId;
        bean.lessonId = lessonId;
        bean.collectionId = collectionId;
        bean.currentItemId = currentItemId;
        bean.currentItemType = currentItemType;
        bean.pathId = pathId;
        bean.pathType = pathType;
        bean.notificationType = notificationType;
        bean.action = action;
        return bean;
    }

    public static class NotificationsConsumerCommandBean {
        private UUID userId;
        private UUID classId;
        private UUID courseId;
        private UUID unitId;
        private UUID lessonId;
        private UUID collectionId;
        private UUID currentItemId;
        private String currentItemType;
        private String pathType;
        private Long pathId;
        private String notificationType;
        private String action;

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public UUID getClassId() {
            return classId;
        }

        public void setClassId(UUID classId) {
            this.classId = classId;
        }

        public UUID getCourseId() {
            return courseId;
        }

        public void setCourseId(UUID courseId) {
            this.courseId = courseId;
        }

        public UUID getUnitId() {
            return unitId;
        }

        public void setUnitId(UUID unitId) {
            this.unitId = unitId;
        }

        public UUID getLessonId() {
            return lessonId;
        }

        public void setLessonId(UUID lessonId) {
            this.lessonId = lessonId;
        }

        public UUID getCollectionId() {
            return collectionId;
        }

        public void setCollectionId(UUID collectionId) {
            this.collectionId = collectionId;
        }

        public UUID getCurrentItemId() {
            return currentItemId;
        }

        public void setCurrentItemId(UUID currentItemId) {
            this.currentItemId = currentItemId;
        }

        public String getCurrentItemType() {
            return currentItemType;
        }

        public void setCurrentItemType(String currentItemType) {
            this.currentItemType = currentItemType;
        }

        public String getPathType() {
            return pathType;
        }

        public void setPathType(String pathType) {
            this.pathType = pathType;
        }

        public Long getPathId() {
            return pathId;
        }

        public void setPathId(Long pathId) {
            this.pathId = pathId;
        }

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }
    }
}
