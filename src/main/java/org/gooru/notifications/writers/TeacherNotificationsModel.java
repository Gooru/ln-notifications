package org.gooru.notifications.writers;

import java.util.List;
import java.util.UUID;

/**
 * @author ashish.
 */

public class TeacherNotificationsModel {
  private Long id;
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
  private List<String> users;
  private Integer occurrence;
  private String milestoneId;
  private String ctxSource;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Integer getOccurrence() {
    return occurrence;
  }

  public void setOccurrence(Integer occurrence) {
    this.occurrence = occurrence;
  }

  public List<String> getUsers() {
    return users;
  }

  public void setUsers(List<String> users) {
    this.users = users;
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

  @Override
  public String toString() {
    return "TeacherNotificationsModel{" + "id=" + id + ", classId=" + classId + ", classCode='"
        + classCode + '\'' + ", courseId=" + courseId + ", unitId=" + unitId + ", lessonId="
        + lessonId + ", collectionId=" + collectionId + ", currentItemId=" + currentItemId
        + ", currentItemTitle='" + currentItemTitle + '\'' + ", currentItemType='" + currentItemType
        + '\'' + ", pathType='" + pathType + '\'' + ", pathId=" + pathId + ", notificationType='"
        + notificationType + '\'' + ", users=" + users + ", occurrence=" + occurrence
        + ", milestoneId='" + milestoneId + '\'' + ", ctxSource='" + ctxSource + '\'' + '}';
  }
}
