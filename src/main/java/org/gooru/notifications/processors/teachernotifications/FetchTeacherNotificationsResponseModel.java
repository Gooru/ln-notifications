package org.gooru.notifications.processors.teachernotifications;

import java.util.UUID;

/**
 * @author ashish.
 */

class FetchTeacherNotificationsResponseModel {
    private Long id;
    private UUID ctxClassId;
    private String ctxClassCode;
    private UUID ctxCourseId;
    private UUID ctxUnitId;
    private UUID ctxLessonId;
    private UUID ctxCollectionId;
    private UUID currentItemId;
    private String currentItemType;
    private String currentItemTitle;
    private String notificationType;
    private Long ctxPathId;
    private String ctxPathType;
    private int occurrence;
    private Long updatedAt;
    private String ctxSource;
    private String milestoneId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCtxClassId() {
        return ctxClassId;
    }

    public void setCtxClassId(UUID ctxClassId) {
        this.ctxClassId = ctxClassId;
    }

    public String getCtxClassCode() {
        return ctxClassCode;
    }

    public void setCtxClassCode(String ctxClassCode) {
        this.ctxClassCode = ctxClassCode;
    }

    public UUID getCtxCourseId() {
        return ctxCourseId;
    }

    public void setCtxCourseId(UUID ctxCourseId) {
        this.ctxCourseId = ctxCourseId;
    }

    public UUID getCtxUnitId() {
        return ctxUnitId;
    }

    public void setCtxUnitId(UUID ctxUnitId) {
        this.ctxUnitId = ctxUnitId;
    }

    public UUID getCtxLessonId() {
        return ctxLessonId;
    }

    public void setCtxLessonId(UUID ctxLessonId) {
        this.ctxLessonId = ctxLessonId;
    }

    public UUID getCtxCollectionId() {
        return ctxCollectionId;
    }

    public void setCtxCollectionId(UUID ctxCollectionId) {
        this.ctxCollectionId = ctxCollectionId;
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

    public String getCurrentItemTitle() {
        return currentItemTitle;
    }

    public void setCurrentItemTitle(String currentItemTitle) {
        this.currentItemTitle = currentItemTitle;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Long getCtxPathId() {
        return ctxPathId;
    }

    public void setCtxPathId(Long ctxPathId) {
        this.ctxPathId = ctxPathId;
    }

    public String getCtxPathType() {
        return ctxPathType;
    }

    public void setCtxPathType(String ctxPathType) {
        this.ctxPathType = ctxPathType;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCtxSource() {
        return ctxSource;
    }

    public void setCtxSource(String ctxSource) {
        this.ctxSource = ctxSource;
    }

    public String getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(String milestoneId) {
        this.milestoneId = milestoneId;
    }
}
