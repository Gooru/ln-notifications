package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class StudentGradableSubmissionNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;

    StudentGradableSubmissionNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {

    }
}
