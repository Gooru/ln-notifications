package org.gooru.notifications.writers;

import org.gooru.notifications.infra.utils.CollectionUtils;
import org.gooru.notifications.infra.utils.UuidUtils;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author ashish.
 */

class AggregatedCountBasedTeacherNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;
    private NotificationsConsumerCommand command;
    private TeacherNotificationsModel model;
    private NotificationsWriterDao dao;
    private static final Logger LOGGER =
        LoggerFactory.getLogger(AggregatedCountBasedTeacherNotificationsWriterService.class);

    AggregatedCountBasedTeacherNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {
        this.command = command;
        setup();
        if (command.isActionInitiate()) {
            createOrUpdateNotification();
        } else {
            resetNotification();
        }
    }

    private void setup() {
        if (command.isOnSystemPath()) {
            command.setCollectionId(fetchCollectionId());
        }
    }

    private void createOrUpdateNotification() {
        if (!notificationExistsForSpecifiedContext()) {
            createModel();
            persistModel();
        } else {
            if (!userIsPartOfExistingNotification()) {
                updateNotificationWithSpecifiedUser();
            } else {
                LOGGER.info("Notification already exists for specified context. Will not create new one.");
            }
        }
    }

    private void updateNotificationWithSpecifiedUser() {
        List<String> users = new ArrayList<>(model.getUsers());
        users.add(command.getUserId().toString());
        getDao().updateExistingNotificationForNewUser(model.getId(), users.size(),
            CollectionUtils.convertToSqlArrayOfString(users));

    }

    private boolean userIsPartOfExistingNotification() {
        return model.getUsers().contains(command.getUserId().toString());
    }

    private void persistModel() {
        List<String> users = new ArrayList<>();
        users.add(command.getUserId().toString());
        model.setOccurrence(users.size());
        getDao().persistTeacherNotification(model, CollectionUtils.convertToSqlArrayOfString(users));
    }

    private void createModel() {
        model = new TeacherNotificationsModel();
        model.setClassId(command.getClassId());
        model.setCourseId(command.getCourseId());
        model.setUnitId(command.getUnitId());
        model.setLessonId(command.getLessonId());
        model.setCollectionId(command.getCollectionId());
        model.setCurrentItemId(command.getCurrentItemId());
        model.setCurrentItemType(command.getCurrentItemType());
        model.setNotificationType(command.getNotificationType());
        model.setPathId(command.getPathId());
        model.setPathType(command.getPathType());
        model.setClassCode(fetchClassCode());
        model.setCurrentItemTitle(fetchCurrentItemTitle());
        model.setOccurrence(1);
    }

    private String fetchClassCode() {
        return getDao().fetchClassCode(command.getClassId());
    }

    private String fetchCurrentItemTitle() {
        return getDao().fetchCollectionTitle(command.getCurrentItemId());
    }

    private UUID fetchCollectionId() {
        if (command.isOnMainPath() || command.isOnRoute0()) {
            return command.getCollectionId();
        } else {
            String ctxCollectionId =
                getDao().fetchCtxCollectionForPath(command.getUserId(), command.getPathId(), command.getPathType());
            return UuidUtils.convertStringToUuid(ctxCollectionId);
        }
    }

    private boolean notificationExistsForSpecifiedContext() {
        model = fetchNotificationWithSpecifiedContext();
        return (model != null);
    }

    private void resetNotification() {
        model = fetchNotificationWithSpecifiedContext();
        deleteTeacherNotificationForSpecifiedUser();
    }

    private void deleteTeacherNotificationForSpecifiedUser() {
        if (model != null && model.getId() != null) {
            // Get the users list and remove the users and then reset the count. Update the users and occurrence
            // If there is no user left, then remove the notification
            UUID notificationForUser = command.getUserId();
            if (model.getUsers() != null && !model.getUsers().isEmpty()) {
                if (!model.getUsers().contains(notificationForUser.toString())) {
                    LOGGER.info("Notification does not exist for specified user. No need to update.");
                } else {
                    List<String> users = new ArrayList<>(model.getUsers());
                    users.remove(notificationForUser.toString());
                    if (users.isEmpty()) {
                        LOGGER.info("Notifications count are reset with this operation. Removing it.");
                        getDao().deleteTeacherNotificationById(model.getId());
                    } else {
                        LOGGER.info("Updating notifications after resetting for this user");
                        getDao().updateExistingNotificationForNewUser(model.getId(), users.size(),
                            CollectionUtils.convertToSqlArrayOfString(users));
                    }
                }
            }
        } else {
            LOGGER.info("No notification found to be reset. Will continue.");
        }
    }

    private TeacherNotificationsModel fetchNotificationWithSpecifiedContext() {
        if (command.getCollectionId() == null) {
            if (command.getPathId() == null) {
                model = getDao().findTeacherNotificationForContextWithoutCollectionAndPath(command.asBean());
            } else {
                model = getDao().findTeacherNotificationForContextWithoutCollection(command.asBean());
            }
        } else {
            if (command.getPathId() == null) {
                model = getDao().findTeacherNotificationForContextWithoutPath(command.asBean());
            } else {
                model = getDao().findTeacherNotificationForContext(command.asBean());
            }
        }
        return model;
    }

    private NotificationsWriterDao getDao() {
        if (dao == null) {
            dao = dbi.onDemand(NotificationsWriterDao.class);
        }
        return dao;
    }

}
