package org.gooru.notifications.processors.teachernotifications;

import org.gooru.notifications.infra.utils.UuidUtils;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author ashish.
 */

public class FetchTeacherNotificationsResponseModelMapper
    implements ResultSetMapper<FetchTeacherNotificationsResponseModel> {

    @Override
    public FetchTeacherNotificationsResponseModel map(int index, ResultSet r, StatementContext ctx)
        throws SQLException {
        FetchTeacherNotificationsResponseModel result = new FetchTeacherNotificationsResponseModel();
        result.setId(r.getLong(Attributes.ID));
        result.setCtxClassId(UuidUtils.convertStringToUuid(r.getString(Attributes.CTX_CLASS_ID)));
        result.setCtxClassCode(r.getString(Attributes.CTX_CLASS_CODE));
        result.setCtxCourseId(UuidUtils.convertStringToUuid(r.getString(Attributes.CTX_COURSE_ID)));
        result.setCtxUnitId(UuidUtils.convertStringToUuid(r.getString(Attributes.CTX_UNIT_ID)));
        result.setCtxLessonId(UuidUtils.convertStringToUuid(r.getString(Attributes.CTX_LESSON_ID)));
        result.setCtxCollectionId(UuidUtils.convertStringToUuid(r.getString(Attributes.CTX_COLLECTION_ID)));
        result.setCurrentItemId(UuidUtils.convertStringToUuid(r.getString(Attributes.CURRENT_ITEM_ID)));
        result.setCurrentItemType(r.getString(Attributes.CURRENT_ITEM_TYPE));
        result.setCurrentItemTitle(r.getString(Attributes.CURRENT_ITEM_TITLE));
        result.setNotificationType(r.getString(Attributes.NOTIFICATION_TYPE));
        result.setCtxPathId(r.getLong(Attributes.CTX_PATH_ID));
        result.setCtxPathType(r.getString(Attributes.CTX_PATH_TYPE));
        result.setOccurrence(r.getInt(Attributes.OCCURRENCE));
        result.setUpdatedAt(r.getDate(Attributes.UPDATED_AT).getTime());
        return result;
    }

    static class Attributes {
        private Attributes() {
            throw new AssertionError();
        }

        static final String ID = "id";
        static final String CTX_CLASS_ID = "ctx_class_id";
        static final String CTX_CLASS_CODE = "ctx_class_code";
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
        static final String OCCURRENCE = "occurrence";
        static final String UPDATED_AT = "updated_at";
    }
}
