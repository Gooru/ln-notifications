package org.gooru.notifications.writers;

import org.gooru.notifications.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

final class NotificationsWriterServiceBuilder {

    NotificationsWriterServiceBuilder() {
        throw new AssertionError();
    }

    static NotificationsWriterService build(String notificationType, DBI dbi) {
        switch (notificationType) {
            case "teacher.override":
                return new TeacherOverrideNotificationsWriterService(dbi);
            case "teacher.suggestion":
                return new TeacherSuggestionNotificationsWriterService(dbi);
            case "teacher.grading.complete":
                return new TeacherGradingCompleteNotificationsWriterService(dbi);
            case "student.self.report":
                return new StudentSelfReportNotificationsWriterService(dbi);
            case "student.gradable.submission":
                return new StudentGradableSubmissionNotificationsWriterService(dbi);
            default:
                return new NotificationsWriterServiceImpl(DBICreator.getDbiForDefaultDS());
        }
    }

    static NotificationsWriterService build(String notificationType) {
        return build(notificationType, DBICreator.getDbiForDefaultDS());
    }


}
