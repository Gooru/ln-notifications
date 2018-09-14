package org.gooru.notifications.processors.teachernotifications;

import org.gooru.notifications.infra.components.AppConfiguration;
import org.gooru.notifications.infra.constants.Constants;
import org.gooru.notifications.infra.constants.HttpConstants;
import org.gooru.notifications.infra.data.EventBusMessage;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.UUID;

/**
 * @author ashish.
 */

class FetchTeacherNotificationsCommand {

    private UUID userId;
    private UUID classId;
    private Long boundary;
    private int limit;
    private static final Logger LOGGER = LoggerFactory.getLogger(FetchTeacherNotificationsCommand.class);

    private FetchTeacherNotificationsCommand(UUID userId, UUID classId, Long boundary, int limit) {
        this.userId = userId;
        this.classId = classId;
        this.boundary = boundary;
        this.limit = limit;
    }

    UUID getUserId() {
        return userId;
    }

    UUID getClassId() {
        return classId;
    }

    Long getBoundary() {
        return boundary;
    }

    int getLimit() {
        return limit;
    }

    boolean applicableForAllClasses() {
        return classId.equals(Constants.Misc.CLASS_PLACEHOLDER);
    }

    FetchTeacherNotificationCommandBean asBean() {
        FetchTeacherNotificationCommandBean result = new FetchTeacherNotificationCommandBean();
        result.setUserId(userId);
        result.setLimit(limit);
        result.setClassId(classId);
        result.setBoundary(boundary);
        return result;
    }

    static FetchTeacherNotificationsCommand builder(EventBusMessage input) {
        UUID userId = input.getUserId();
        UUID classId;
        int limit;
        Long boundary = null;

        try {
            classId = getClassId(input);
            limit = getLimit(input);
            boundary = getBoundary(input);
        } catch (Throwable e) {
            LOGGER.info("Exception while creating command object", e);
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return new FetchTeacherNotificationsCommand(userId, classId, boundary, limit);
    }

    private static UUID getClassId(EventBusMessage input) {
        UUID classId;
        String classIdString = input.getRequestBody().getString(CommandAttributes.CLASS_ID);
        if (classIdString == null || classIdString.isEmpty()) {
            classId = Constants.Misc.CLASS_PLACEHOLDER;
        } else {
            try {
                classId = UUID.fromString(classIdString);
            } catch (Exception e) {
                LOGGER.warn("Invalid UUID string for classId", e);
                throw new IllegalArgumentException("Invalid UUID format for classId.");
            }
        }
        return classId;
    }

    private static int getLimit(EventBusMessage input) {
        String limitString = input.getRequestBody().getString(CommandAttributes.LIMIT);
        int limit;
        if (limitString != null && !limitString.isEmpty()) {
            try {
                limit = Integer.valueOf(limitString);
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid number for limit", e);
                throw new IllegalArgumentException("Invalid number for limit");
            }
            if (limit > AppConfiguration.getInstance().getMaxLimit()) {
                throw new IllegalArgumentException("Provided limit exceeds the max limit allowed");
            }
        } else {
            limit = AppConfiguration.getInstance().getDefaultLimit();
        }
        return limit;
    }

    private static Long getBoundary(EventBusMessage input) {
        Long boundary = null;
        String boundaryString = input.getRequestBody().getString(CommandAttributes.BOUNDARY);
        if (boundaryString != null && !boundaryString.isEmpty()) {
            try {
                boundary = (Long.valueOf(boundaryString) + 1)/1000;
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid number for boundary", e);
                throw new IllegalArgumentException("Invalid number for boundary");
            }
        } else {
            boundary = new Date().getTime();
        }
        return boundary;
    }

    @Override
    public String toString() {
        return "FetchTeacherNotificationsCommand{" + "userId=" + userId + ", classId=" + classId + ", boundary=" +
                   boundary + ", limit=" + limit + '}';
    }

    public static class FetchTeacherNotificationCommandBean {
        private UUID userId;
        private UUID classId;
        private Long boundary;
        private int limit;

        public UUID getUserId() {
            return userId;
        }

        public void setUserId(UUID userId) {
            this.userId = userId;
        }

        public UUID getClassId() {
            return classId;
        }

        public void setClassId(UUID classId) {
            this.classId = classId;
        }

        public Long getBoundary() {
            return boundary;
        }

        public void setBoundary(Long boundary) {
            this.boundary = boundary;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }
    }

    static final class CommandAttributes {
        static final String CLASS_ID = "classId";
        static final String LIMIT = "limit";
        static final String BOUNDARY = "boundary";

        private CommandAttributes() {
            throw new AssertionError();
        }
    }
}
