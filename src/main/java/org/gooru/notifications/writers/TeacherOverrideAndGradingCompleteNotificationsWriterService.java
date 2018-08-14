package org.gooru.notifications.writers;

import org.gooru.notifications.infra.utils.UuidUtils;
import org.skife.jdbi.v2.DBI;

import java.util.UUID;

/**
 * This writer is triggered when teacher overrides score for an item for specific student.
 *
 * The notification is initiated from teacher, for specific student from Insights module. Hence, it won't be
 * having access to collection as well as current item, at least as of now. Hence this writer would need to resort to
 * hack of populating current context based on path params (id and type).
 *
 * @author ashish.
 */

class TeacherOverrideAndGradingCompleteNotificationsWriterService implements NotificationsWriterService {


    private final DBI dbi;
    private NotificationsConsumerCommand command;
    private StudentNotificationsModel model;
    private NotificationsWriterDao dao;

    TeacherOverrideAndGradingCompleteNotificationsWriterService(DBI dbi) {
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
        model.setCollectionId(fetchCollectionId());
        model.setCurrentItemId(command.getCurrentItemId());
        model.setCurrentItemType(command.getCurrentItemType());
        model.setNotificationType(command.getNotificationType());
        model.setPathId(command.getPathId());
        model.setPathType(command.getPathType());
        model.setClassCode(fetchClassCode());
        model.setCurrentItemTitle(fetchCurrentItemTitle());
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
        getDao().deleteStudentNotificationById(model.getId());
    }

    private StudentNotificationsModel fetchNotificationWithSpecifiedContext() {
        if (command.getCollectionId() == null) {
            if (command.getPathId() == null) {
                model = getDao().findStudentNotificationForContextWithoutCollectionAndPath(command.getEvent());
            } else {
                model = getDao().findStudentNotificationForContextWithoutCollection(command.getEvent());
            }
        } else {
            if (command.getPathId() == null) {
                model = getDao().findStudentNotificationForContextWithoutPath(command.getEvent());
            } else {
                model = getDao().findStudentNotificationForContext(command.getEvent());
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
