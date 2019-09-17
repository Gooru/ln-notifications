package org.gooru.notifications.writers;

import org.gooru.notifications.infra.jdbi.DBICreator;
import org.gooru.notifications.infra.utils.UuidUtils;
import org.gooru.notifications.writers.milestones.MilestoneFinder;
import org.gooru.notifications.writers.milestones.MilestoneFinderContext;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * This writer is triggered when teacher overrides score for an item for specific student.
 * <p>
 * The notification is initiated from teacher, for specific student from Insights module. Hence, it
 * won't be having access to collection as well as current item, at least as of now. Hence this
 * writer would need to resort to hack of populating current context based on path params (id and
 * type).
 *
 * @author ashish.
 */

class TeacherOverrideAndGradingCompleteNotificationsWriterService
    implements NotificationsWriterService {


  private final DBI dbi;
  private NotificationsConsumerCommand command;
  private StudentNotificationsModel model;
  private NotificationsWriterDao dao;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(TeacherOverrideAndGradingCompleteNotificationsWriterService.class);

  TeacherOverrideAndGradingCompleteNotificationsWriterService(DBI dbi) {
    this.dbi = dbi;
  }


  @Override
  public void handleNotifications(NotificationsConsumerCommand command) {
    this.command = command;
    setup();
    if (command.isActionInitiate()) {
      createNotification();
    } else {
      resetNotification();
    }
  }

  private void setup() {
    if (command.isOnSystemPath()) {
      command.setCollectionId(fetchCollectionId());
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
    model.setCtxSource(command.getCtxSource());
    model.setMilestoneId(MilestoneFinder
        .build(DBICreator.getDbiForDefaultDS(), buildMilestoneFinderContext()).findMilestone());
  }

  private MilestoneFinderContext buildMilestoneFinderContext() {
    return new MilestoneFinderContext(command.getClassId(), command.getCourseId(),
        command.getUnitId(), command.getLessonId());
  }

  private UUID fetchCollectionId() {
    if (command.isOnMainPath() || command.isOnRoute0()) {
      return command.getCollectionId();
    } else {
      String ctxCollectionId = getDao().fetchCtxCollectionForPath(command.getUserId(),
          command.getPathId(), command.getPathType());
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
    if (model != null && model.getId() != null) {
      getDao().deleteStudentNotificationById(model.getId());
    } else {
      LOGGER.info("No notification found to be reset. Will continue.");
    }
  }

  private StudentNotificationsModel fetchNotificationWithSpecifiedContext() {
    if (command.getCollectionId() == null) {
      if (command.getPathId() == null) {
        model =
            getDao().findStudentNotificationForContextWithoutCollectionAndPath(command.asBean());
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
