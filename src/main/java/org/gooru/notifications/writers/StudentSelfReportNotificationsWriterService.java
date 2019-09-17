package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;

/**
 * This writer is triggered when student does a self report on an item for the teachers.
 * <p>
 * The notification is initiated from student, for specific class from Insights module. Hence, it
 * won't be having access to collection as well as current item. Thus this handler would require a
 * hack to actually read and populate correct context based on path id/type if present.
 *
 * @author ashish.
 */

class StudentSelfReportNotificationsWriterService implements NotificationsWriterService {
  private final DBI dbi;

  StudentSelfReportNotificationsWriterService(DBI dbi) {
    this.dbi = dbi;
  }

  @Override
  public void handleNotifications(NotificationsConsumerCommand command) {
    new AggregatedCountBasedTeacherNotificationsWriterService(dbi).handleNotifications(command);
  }

}
