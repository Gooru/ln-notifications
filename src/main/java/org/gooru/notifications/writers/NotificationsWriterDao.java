package org.gooru.notifications.writers;

import org.gooru.notifications.infra.jdbi.PGArray;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.UUID;

import static org.gooru.notifications.writers.NotificationsConsumerCommand.NotificationsConsumerCommandBean;

interface NotificationsWriterDao {

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_class_id = :classId and ctx_course_id = " +
            ":courseId and ctx_unit_id = :unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and " +
            "current_item_id = :currentItemId and current_item_type = :currentItemType and notification_type = " +
            ":notificationType and ctx_path_id is null and ctx_path_type is null")
    StudentNotificationsModel findStudentNotificationForContextWithoutCollectionAndPath(
        @BindBean NotificationsConsumerCommandBean model);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_class_id = :classId and ctx_course_id = " +
            ":courseId and ctx_unit_id = :unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and " +
            "current_item_id = :currentItemId and current_item_type = :currentItemType and notification_type = " +
            ":notificationType and ctx_path_id = :pathId and ctx_path_type = :pathType")
    StudentNotificationsModel findStudentNotificationForContextWithoutCollection(
        @BindBean NotificationsConsumerCommandBean model);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_class_id = :classId and ctx_course_id = " +
            ":courseId and ctx_unit_id = :unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId " +
            "and current_item_id = :currentItemId and current_item_type = :currentItemType and notification_type = " +
            ":notificationType and ctx_path_id is null and ctx_path_type is null")
    StudentNotificationsModel findStudentNotificationForContextWithoutPath(
        @BindBean NotificationsConsumerCommandBean model);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_class_id = :classId and ctx_course_id = " +
            ":courseId and ctx_unit_id = :unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId " +
            "and current_item_id = :currentItemId and current_item_type = :currentItemType and notification_type = " +
            ":notificationType and ctx_path_id = :pathId and ctx_path_type = :pathType")
    StudentNotificationsModel findStudentNotificationForContext(
        @BindBean NotificationsConsumerCommandBean model);

    @SqlUpdate("delete from student_notifications where id = :id")
    void deleteStudentNotificationById(@Bind("id") Long id);

    @SqlQuery("select code from class where id = :classId")
    String fetchClassCode(@Bind("classId") UUID classId);

    @SqlQuery("select title from collection where id = :currentItemId")
    String fetchCollectionTitle(@Bind("currentItemId") UUID currentItemId);

    @SqlUpdate(
        "insert into student_notifications (ctx_user_id, ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, " +
            "ctx_lesson_id, ctx_collection_id, current_item_id, current_item_type, current_item_title, " +
            "notification_type, ctx_path_id, ctx_path_type) values (:userId, :classId, :classCode, :courseId, " +
            ":unitId, :lessonId, :collectionId, :currentItemId, :currentItemType, :currentItemTitle, " +
            ":notificationType, :pathId, :pathType)")
    void persistStudentNotification(@BindBean StudentNotificationsModel model);

    @SqlQuery("select ctx_collection_id from user_navigation_paths where id = :pathId and ctx_user_id = :userId and " +
                  "suggestion_type = :pathType")
    String fetchCtxCollectionForPath(@Bind("userId") UUID userId, @Bind("pathId") Long pathId,
        @Bind("pathType") String pathType);

    @SqlUpdate("delete from teacher_notifications where id = :id")
    void deleteTeacherNotificationById(@Bind("id") Long id);

    @SqlUpdate(
        "insert into teacher_notifications (ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, " +
            "ctx_lesson_id, ctx_collection_id, current_item_id, current_item_type, current_item_title, " +
            "notification_type, ctx_path_id, ctx_path_type, occurrence, users) values (:classId, :classCode," +
            " :courseId, :unitId, :lessonId, :collectionId, :currentItemId, :currentItemType, :currentItemTitle, " +
            ":notificationType, :pathId, :pathType, :occurrence, :usersArray)")
    void persistTeacherNotification(@BindBean TeacherNotificationsModel model,
        @Bind("usersArray")PGArray<String> usersArray);


    @SqlUpdate("update teacher_notifications set occurrence = :occurrence, users = :usersArray where id = :id")
    void updateExistingNotificationForNewUser(@Bind("id") Long id, @Bind("occurrence") int occurrence, @Bind(
        "usersArray") PGArray<String> usersArray);

    @Mapper(TeacherNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, current_item_id, " +
            "current_item_type, notification_type, ctx_path_id, ctx_path_type, occurrence, users " +
            "from teacher_notifications where ctx_class_id = :classId and ctx_course_id = :courseId and ctx_unit_id =" +
            " :unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id is null and ctx_path_type is null")
    TeacherNotificationsModel findTeacherNotificationForContextWithoutCollectionAndPath(@BindBean NotificationsConsumerCommandBean bean);

    @Mapper(TeacherNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, current_item_id, " +
            "current_item_type, notification_type, ctx_path_id, ctx_path_type, occurrence, users " +
            "from teacher_notifications where ctx_class_id = :classId and ctx_course_id = :courseId and ctx_unit_id =" +
            " :unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id = :pathId and ctx_path_type = :pathType")
    TeacherNotificationsModel findTeacherNotificationForContextWithoutCollection(@BindBean NotificationsConsumerCommandBean bean);

    @Mapper(TeacherNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, current_item_id, " +
            "current_item_type, notification_type, ctx_path_id, ctx_path_type, occurrence, users " +
            "from teacher_notifications where ctx_class_id = :classId and ctx_course_id = :courseId and ctx_unit_id =" +
            " :unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id is null and ctx_path_type is null")
    TeacherNotificationsModel findTeacherNotificationForContextWithoutPath(@BindBean NotificationsConsumerCommandBean bean);

    @Mapper(TeacherNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, current_item_id, " +
            "current_item_type, notification_type, ctx_path_id, ctx_path_type, occurrence, users " +
            "from teacher_notifications where ctx_class_id = :classId and ctx_course_id = :courseId and ctx_unit_id =" +
            " :unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id = :pathId and ctx_path_type = :pathType")
    TeacherNotificationsModel findTeacherNotificationForContext(@BindBean NotificationsConsumerCommandBean bean);
}
