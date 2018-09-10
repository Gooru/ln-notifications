package org.gooru.notifications.processors.studentnotifications;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;
import java.util.UUID;

/**
 * @author ashish.
 */

interface FetchStudentNotificationsDao {


    @SqlQuery("select exists (select 1 from class_member where class_id = (select id from class where id = :classId " +
                  "and is_deleted = false) and user_id = :userId)")
    boolean checkUserIsStudentForClass(@Bind("classId") UUID classId, @Bind("userId") UUID userId);


    @Mapper(FetchStudentNotificationsResponseModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            " current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type, " +
            " updated_at from student_notifications where ctx_class_id = :classId and ctx_user_id = :userId and " +
            " to_timestamp(:boundary) > updated_at order by updated_at desc limit :limit")
    List<FetchStudentNotificationsResponseModel> fetchNotificationsForSpecificClass(
        @BindBean FetchStudentNotificationsCommand.FetchStudentNotificationCommandBean model);

    @Mapper(FetchStudentNotificationsResponseModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            "current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type, " +
            "updated_at from student_notifications where ctx_class_id = any(select id from class c inner join " +
            "class_member cm on cm.class_id = c.id where cm.user_id = :userId and c.is_deleted = false)" +
            " and to_timestamp(:boundary) > updated_at order by updated_at desc limit :limit")
    List<FetchStudentNotificationsResponseModel> fetchNotificationsForAllClasses(
        @BindBean FetchStudentNotificationsCommand.FetchStudentNotificationCommandBean model);


}
