package org.gooru.notifications.writers;

import java.util.UUID;

/**
 * @author ashish.
 */

public class StudentNotificationsModel {
    private Long id;
    private UUID userId;
    private UUID classId;
    private String classCode;
    private UUID courseId;
    private UUID unitId;
    private UUID lessonId;
    private UUID collectionId;
    private UUID currentItemId;
    private String currentItemTitle;
    private String currentItemType;
    private String pathType;
    private Long pathId;
    private String notificationType;
    private String milestoneId;
    private String ctxSource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
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

    public String getCurrentItemTitle() {
        return currentItemTitle;
    }

    public void setCurrentItemTitle(String currentItemTitle) {
        this.currentItemTitle = currentItemTitle;
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

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getCtxSource() {
        return ctxSource;
    }

    public void setCtxSource(String ctxSource) {
        this.ctxSource = ctxSource;
    }
}
