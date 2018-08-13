package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class TeacherGradingCompleteNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;

    TeacherGradingCompleteNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {

    }
}
