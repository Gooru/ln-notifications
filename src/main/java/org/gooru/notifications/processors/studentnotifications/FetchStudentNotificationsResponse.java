package org.gooru.notifications.processors.studentnotifications;


import java.util.List;

/**
 * @author ashish.
 */

class FetchStudentNotificationsResponse {

    private List<FetchStudentNotificationsResponseModel> notifications;
    private Long boundary;
    private boolean moreItemsRemaining = false;

    public List<FetchStudentNotificationsResponseModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<FetchStudentNotificationsResponseModel> notifications) {
        this.notifications = notifications;
    }

    public Long getBoundary() {
        return boundary;
    }

    public void setBoundary(Long boundary) {
        this.boundary = boundary;
    }

    public boolean isMoreItemsRemaining() {
        return moreItemsRemaining;
    }

    public void setMoreItemsRemaining(boolean moreItemsRemaining) {
        this.moreItemsRemaining = moreItemsRemaining;
    }
}
