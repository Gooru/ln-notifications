package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class NotificationsWriterServiceImpl implements NotificationsWriterService {

    private final DBI dbi;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationsWriterServiceImpl.class);

    NotificationsWriterServiceImpl(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {
        LOGGER.error("No writer found for command: '{}'", command.toString());
        throw new IllegalArgumentException("No handler found for notification type:" + command.getNotificationType());
    }
}
