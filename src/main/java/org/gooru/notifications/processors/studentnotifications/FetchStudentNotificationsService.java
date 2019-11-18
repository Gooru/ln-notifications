package org.gooru.notifications.processors.studentnotifications;

import org.gooru.notifications.infra.constants.HttpConstants;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author ashish.
 */

class FetchStudentNotificationsService {

  private DBI dbi;
  private FetchStudentNotificationsDao dao;
  private FetchStudentNotificationsCommand command;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(FetchStudentNotificationsService.class);
  private FetchStudentNotificationsResponse response;
  private List<FetchStudentNotificationsResponseModel> models;
  private boolean moreRemaining = false;

  FetchStudentNotificationsService(DBI dbi) {
    this.dbi = dbi;
  }


  FetchStudentNotificationsResponse fetch(FetchStudentNotificationsCommand command) {
    this.command = command;
    LOGGER.debug("Processing command: {}", command.toString());
    if (command.applicableForAllClasses()) {
      return fetchStudentNotificationsForAllClasses();
    } else {
      return fetchStudentNotificationForSingleClass();
    }
  }

  private FetchStudentNotificationsResponse fetchStudentNotificationForSingleClass() {
    if (!getDao().checkUserIsStudentForClass(command.getClassId(), command.getUserId())) {
      LOGGER.warn("User '{}' is not student for class '{}' or class does not exists",
          command.getUserId(), command.getClassId());
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.FORBIDDEN,
          "Not authorized as Student or class does not exist");
    }
    models = getDao().fetchNotificationsForSpecificClass(fetchStudentNotificationCommandBean());
    return createResponseFromModels();
  }

  private FetchStudentNotificationsResponse fetchStudentNotificationsForAllClasses() {
    models = getDao().fetchNotificationsForAllClasses(fetchStudentNotificationCommandBean());
    return createResponseFromModels();
  }

  private FetchStudentNotificationsCommand.FetchStudentNotificationCommandBean fetchStudentNotificationCommandBean() {
    // Fetch one more than needed so that caller could be informed about availability of more items
    FetchStudentNotificationsCommand.FetchStudentNotificationCommandBean result = command.asBean();
    result.setLimit(result.getLimit() + 1);
    return result;
  }

  private FetchStudentNotificationsResponse createResponseFromModels() {
    preprocessModels();
    response = new FetchStudentNotificationsResponse();
    response.setNotifications(models);
    if (!models.isEmpty()) {
      response.setBoundary((models.get(models.size() - 1).getUpdatedAt()));
    } else {
      response.setBoundary(null);
    }
    response.setMoreItemsRemaining(moreRemaining);
    return response;
  }

  private void preprocessModels() {
    if (command.getLimit() < models.size()) {
      moreRemaining = true;
      models.remove(models.size() - 1);
    }
  }

  private FetchStudentNotificationsDao getDao() {
    if (dao == null) {
      dao = dbi.onDemand(FetchStudentNotificationsDao.class);
    }
    return dao;
  }
}
