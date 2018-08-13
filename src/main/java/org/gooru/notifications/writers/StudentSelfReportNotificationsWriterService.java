package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class StudentSelfReportNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;

    StudentSelfReportNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {

    }
}
