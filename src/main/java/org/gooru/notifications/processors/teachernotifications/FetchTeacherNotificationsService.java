package org.gooru.notifications.processors.teachernotifications;

import org.gooru.notifications.infra.constants.HttpConstants;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @author ashish.
 */

class FetchTeacherNotificationsService {

  private DBI dbi;
  private FetchTeacherNotificationsDao dao;
  private FetchTeacherNotificationsCommand command;
  private static final Logger LOGGER =
      LoggerFactory.getLogger(FetchTeacherNotificationsService.class);
  private FetchTeacherNotificationsResponse response;
  private List<FetchTeacherNotificationsResponseModel> models;
  private boolean moreRemaining = false;

  FetchTeacherNotificationsService(DBI dbi) {
    this.dbi = dbi;
  }


  FetchTeacherNotificationsResponse fetch(FetchTeacherNotificationsCommand command) {
    this.command = command;
    LOGGER.debug("Processing command: {}", command.toString());
    if (command.applicableForAllClasses()) {
      return fetchTeacherNotificationsForAllClasses();
    } else {
      return fetchTeacherNotificationForSingleClass();
    }
  }

  private FetchTeacherNotificationsResponse fetchTeacherNotificationForSingleClass() {
    if (!getDao().checkUserIsAuthorizedAsTeacherForClass(command.getClassId(),
        command.getUserId())) {
      LOGGER.warn("User '{}' is not authorized for class '{}' or class does not exists",
          command.getUserId(), command.getClassId());
      throw new HttpResponseWrapperException(HttpConstants.HttpStatus.FORBIDDEN,
          "Not authorized as teacher or coteacher or class does not exist");
    }
    models = getDao().fetchNotificationsForSpecificClass(fetchTeacherNotificationCommandBean());
    return createResponseFromModels();
  }

  private FetchTeacherNotificationsResponse fetchTeacherNotificationsForAllClasses() {
    models = getDao().fetchNotificationsForAllClasses(fetchTeacherNotificationCommandBean());
    return createResponseFromModels();
  }

  private FetchTeacherNotificationsCommand.FetchTeacherNotificationCommandBean fetchTeacherNotificationCommandBean() {
    // Fetch one more than needed so that caller could be informed about availability of more items
    FetchTeacherNotificationsCommand.FetchTeacherNotificationCommandBean result = command.asBean();
    result.setLimit(result.getLimit() + 1);
    return result;
  }

  private FetchTeacherNotificationsResponse createResponseFromModels() {
    preprocessModels();
    response = new FetchTeacherNotificationsResponse();
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

  private FetchTeacherNotificationsDao getDao() {
    if (dao == null) {
      dao = dbi.onDemand(FetchTeacherNotificationsDao.class);
    }
    return dao;
  }
}
