package org.gooru.notifications.writers;

interface NotificationsWriterService {
    void handleNotifications(NotificationsConsumerCommand command);
}
