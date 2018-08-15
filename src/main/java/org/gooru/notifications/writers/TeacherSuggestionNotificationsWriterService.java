package org.gooru.notifications.writers;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This writer is triggered when teacher assigns a suggestion to student.
 * <p>
 * The notification is initiated from teacher, for specific student from Navigate Next module. Hence, it should be
 * having access to collection as well as current item. Thus this handler won't require a hack to actually read and
 * populate correct context based on path id/type if present.
 *
 * @author ashish.
 */

class TeacherSuggestionNotificationsWriterService implements NotificationsWriterService {
    private final DBI dbi;
    private NotificationsConsumerCommand command;
    private StudentNotificationsModel model;
    private NotificationsWriterDao dao;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherSuggestionNotificationsWriterService.class);

    TeacherSuggestionNotificationsWriterService(DBI dbi) {
        this.dbi = dbi;
    }

    @Override
    public void handleNotifications(NotificationsConsumerCommand command) {
        this.command = command;
        if (command.isActionInitiate()) {
            createNotification();
        } else {
            resetNotification();
        }
    }

    private void createNotification() {
        if (!notificationExistsForSpecifiedContext()) {
            createModel();
            persistModel();
        } else {
            LOGGER.info("Notification already exists for specified context. Will not create new one.");
        }
    }

    private void persistModel() {
        getDao().persistStudentNotification(model);
    }

    private void createModel() {
        model = new StudentNotificationsModel();
        model.setUserId(command.getUserId());
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
    }

    private String fetchClassCode() {
        return getDao().fetchClassCode(command.getClassId());
    }

    private String fetchCurrentItemTitle() {
        return getDao().fetchCollectionTitle(command.getCurrentItemId());
    }

    private boolean notificationExistsForSpecifiedContext() {
        model = fetchNotificationWithSpecifiedContext();
        return (model != null);
    }

    private void resetNotification() {
        model = fetchNotificationWithSpecifiedContext();
        deleteStudentNotificationById();
    }

    private void deleteStudentNotificationById() {
        if (model != null && model.getId() != null) {
            getDao().deleteStudentNotificationById(model.getId());
        } else {
            LOGGER.info("No notification found to be reset. Will continue.");
        }
    }

    private StudentNotificationsModel fetchNotificationWithSpecifiedContext() {
        if (command.getCollectionId() == null) {
            if (command.getPathId() == null) {
                model = getDao().findStudentNotificationForContextWithoutCollectionAndPath(command.asBean());
            } else {
                model = getDao().findStudentNotificationForContextWithoutCollection(command.asBean());
            }
        } else {
            if (command.getPathId() == null) {
                model = getDao().findStudentNotificationForContextWithoutPath(command.asBean());
            } else {
                model = getDao().findStudentNotificationForContext(command.asBean());
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
