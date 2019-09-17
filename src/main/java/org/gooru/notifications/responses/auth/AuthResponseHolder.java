package org.gooru.notifications.responses.auth;

public interface AuthResponseHolder {
  boolean isAuthorized();

  boolean isAnonymous();

  String getUser();
}
