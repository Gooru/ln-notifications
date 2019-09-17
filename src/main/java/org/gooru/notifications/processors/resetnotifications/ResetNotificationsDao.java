package org.gooru.notifications.processors.resetnotifications;

import org.gooru.notifications.infra.jdbi.UUIDMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import java.util.UUID;

/**
 * @author ashish.
 */

interface ResetNotificationsDao {

  @SqlQuery("select exists (select 1 from class where id = :classId and (creator_id = :userId or collaborator ?? "
      + ":userId::text ) and is_deleted = false )")
  boolean checkUserIsAuthorizedAsTeacherForClass(@Bind("classId") UUID classId,
      @Bind("userId") UUID userId);

  @Mapper(UUIDMapper.class)
  @SqlQuery("select ctx_class_id from teacher_notifications where id = :id")
  UUID fetchClassIdForTeacherNotification(@Bind("id") Long id);

  @SqlUpdate("delete from student_notifications where id = :id and ctx_user_id = :userId")
  void deleteStudentNotification(@Bind("id") Long id, @Bind("userId") UUID userId);

  @SqlUpdate("delete from teacher_notifications where id = :id")
  void deleteTeacherNotification(@Bind("id") Long id);

  @SqlQuery("select is_actionable from student_notifications_master where name = (select notification_type from "
      + "student_notifications where id = :id)")
  Boolean isStudentNotificationActionable(@Bind("id") Long id);

  @SqlQuery("select is_actionable from teacher_notifications_master where name = (select notification_type from "
      + "teacher_notifications where id = :id)")
  Boolean isTeacherNotificationActionable(@Bind("id") Long id);

}
