package org.gooru.notifications.writers;

/**
 * @author ashish.
 */

final class ContextSourceFinder {
    private static final String COURSE_MAP = "course-map";
    private static final String CLASS_ACTIVITY = "class-activity";

    private ContextSourceFinder() {
        throw new AssertionError();
    }

    static String findContextSource(NotificationsEvent event) {

        return COURSE_MAP;
    }
}
