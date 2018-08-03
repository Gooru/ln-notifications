package org.gooru.notifications.processors.teachernotifications;

import java.util.List;

/**
 * @author ashish.
 */

class FetchTeacherNotificationsResponse {

    private List<FetchTeacherNotificationsResponseModel> notifications;
    private Long boundary;
    private boolean moreItemsRemaining = false;

    public List<FetchTeacherNotificationsResponseModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<FetchTeacherNotificationsResponseModel> notifications) {
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
