package org.gooru.notifications.writers;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.UUID;

interface NotificationsWriterDao {

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_course_id = :courseId and ctx_unit_id = " +
            ":unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id is null and ctx_path_type is null")

    StudentNotificationsModel findStudentNotificationForContextWithoutCollectionAndPath(NotificationsEvent event);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_course_id = :courseId and ctx_unit_id = " +
            ":unitId and ctx_lesson_id = :lessonId and ctx_collection_id is null and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id = :pathId and ctx_path_type = :pathType")
    StudentNotificationsModel findStudentNotificationForContextWithoutCollection(NotificationsEvent event);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_course_id = :courseId and ctx_unit_id = " +
            ":unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id is null and ctx_path_type is null")
    StudentNotificationsModel findStudentNotificationForContextWithoutPath(NotificationsEvent event);

    @Mapper(StudentNotificationsModelMapper.class)
    @SqlQuery(
        "select id, ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type " +
            "from student_notifications where ctx_user_id = :userId and ctx_course_id = :courseId and ctx_unit_id = " +
            ":unitId and ctx_lesson_id = :lessonId and ctx_collection_id = :collectionId and current_item_id = " +
            ":currentItemId and current_item_type = :currentItemType and notification_type = :notificationType and " +
            "ctx_path_id = :pathId and ctx_path_type = :pathType")
    StudentNotificationsModel findStudentNotificationForContext(NotificationsEvent event);

    @SqlUpdate("delete from student_notifications where id = :id")
    void deleteStudentNotificationById(@Bind("id") Long id);

    @SqlQuery("select code from class where id = :classId")
    String fetchClassCode(@Bind("classId") UUID classId);

    @SqlQuery("select title from collection where id = :currentItemId")
    String fetchCollectionTitle(@Bind("currentItemId") UUID currentItemId);

    @SqlUpdate("insert into student_notifications ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, " +
                   "ctx_lesson_id, ctx_collection_id, current_item_id, current_item_type, current_item_title, " +
                   "notification_type, ctx_path_id, ctx_path_type values (:userId, :classId, :courseId, :unitId, " +
                   ":lessonId, :collectionId, :currentItemId, :currentItemType, :currentItemTitle, :notificationType," +
                   " :pathId, :pathType)")
    void persistStudentNotification(@BindBean StudentNotificationsModel model);
}
