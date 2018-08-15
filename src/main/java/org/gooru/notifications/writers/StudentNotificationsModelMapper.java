package org.gooru.notifications.writers;

import org.gooru.notifications.infra.utils.UuidUtils;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ashish.
 */

public class StudentNotificationsModelMapper implements ResultSetMapper<StudentNotificationsModel> {

    @Override
    public StudentNotificationsModel map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        StudentNotificationsModel model = new StudentNotificationsModel();
        model.setId(r.getLong(AttributeNames.ID));
        model.setUserId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_USER_ID)));
        model.setClassId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_CLASS_ID)));
        model.setCourseId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_COURSE_ID)));
        model.setUnitId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_UNIT_ID)));
        model.setLessonId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_LESSON_ID)));
        model.setCollectionId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CTX_COLLECTION_ID)));
        model.setCurrentItemId(UuidUtils.convertStringToUuid(r.getString(AttributeNames.CURRENT_ITEM_ID)));
        model.setCurrentItemType(r.getString(AttributeNames.CURRENT_ITEM_TYPE));
        model.setCurrentItemTitle(r.getString(AttributeNames.CURRENT_ITEM_TITLE));
        model.setNotificationType(r.getString(AttributeNames.NOTIFICATION_TYPE));
        model.setPathId(r.getLong(AttributeNames.CTX_PATH_ID));
        model.setPathType(r.getString(AttributeNames.CTX_PATH_TYPE));
        return model;
    }

    final static class AttributeNames {
        AttributeNames() {
            throw new AssertionError();
        }

        static final String ID = "id";
        static final String CTX_USER_ID = "ctx_user_id";
        static final String CTX_CLASS_ID = "ctx_class_id";
        static final String CTX_COURSE_ID = "ctx_course_id";
        static final String CTX_UNIT_ID = "ctx_unit_id";
        static final String CTX_LESSON_ID = "ctx_lesson_id";
        static final String CTX_COLLECTION_ID = "ctx_collection_id";
        static final String CURRENT_ITEM_ID = "current_item_id";
        static final String CURRENT_ITEM_TYPE = "current_item_type";
        static final String CURRENT_ITEM_TITLE = "current_item_title";
        static final String NOTIFICATION_TYPE = "notification_type";
        static final String CTX_PATH_ID = "ctx_path_id";
        static final String CTX_PATH_TYPE = "ctx_path_type";

    }
}
