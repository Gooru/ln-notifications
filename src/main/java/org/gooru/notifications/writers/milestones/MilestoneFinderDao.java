package org.gooru.notifications.writers.milestones;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import java.util.UUID;

/**
 * @author ashish.
 */

public interface MilestoneFinderDao {

  @SqlQuery("select grade_current from class where id = :classId and is_deleted = false and milestone_view_applicable = "
      + " true and course_id = (select id from course where id = :courseId and is_deleted = false and version = "
      + " 'premium')")
  Long fetchCurrentGrade(@Bind("classId") UUID classId, @Bind("courseId") UUID courseId);

  @SqlQuery("select milestone_id from milestone_lesson_map where course_id = :courseId and unit_id = :unitId and "
      + "lesson_id = :lessonId and fw_code = (select fw_code from grade_master where id = "
      + ":destinationGrade) limit 1")
  String fetchMilestoneId(@Bind("courseId") UUID courseId, @Bind("unitId") UUID unitId,
      @Bind("lessonId") UUID lessonId, @Bind("destinationGrade") Long destinationGrade);

}
