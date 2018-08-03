package org.gooru.notifications.processors.teachernotifications;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;
import java.util.UUID;

/**
 * @author ashish.
 */

interface FetchTeacherNotificationsDao {

    @SqlQuery("select exists (select 1 from class where id = :classId and (creator_id = :userId or collaborator ?? " +
                  ":userId::text ) and is_deleted = false )")
    boolean checkUserIsAuthorizedAsTeacherForClass(@Bind("classId") UUID classId, @Bind("userId") UUID userId);


    @Mapper(FetchTeacherNotificationsResponseModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            " current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type, " +
            " occurrence, updated_at from teacher_notifications where ctx_class_id = :classId and " +
            " to_timestamp(:boundary) > updated_at order by updated_at desc limit :limit")
    List<FetchTeacherNotificationsResponseModel> fetchNotificationsForSpecificClass(
        @BindBean FetchTeacherNotificationsCommand.FetchTeacherNotificationCommandBean model);

    @Mapper(FetchTeacherNotificationsResponseModelMapper.class)
    @SqlQuery(
        "select id, ctx_class_id, ctx_class_code, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id, " +
            " current_item_id, current_item_type, current_item_title, notification_type, ctx_path_id, ctx_path_type, " +
            " occurrence, updated_at from teacher_notifications where ctx_class_id = any(select id from class where " +
            " (creator_id = :userId or collaborator ?? :userId::text) and is_deleted = false) and " +
            " to_timestamp(:boundary) > updated_at order by updated_at desc limit :limit")
    List<FetchTeacherNotificationsResponseModel> fetchNotificationsForAllClasses(
        @BindBean FetchTeacherNotificationsCommand.FetchTeacherNotificationCommandBean model);

}
