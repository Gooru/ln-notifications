package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class TeacherSuggestionNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;

    public TeacherSuggestionNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {

    }
}
