
ALTER TABLE teacher_notifications ADD COLUMN milestone_id TEXT;
ALTER TABLE teacher_notifications ADD COLUMN ctx_source TEXT DEFAULT 'course-map';
UPDATE teacher_notifications SET ctx_source = 'course-map';
ALTER TABLE teacher_notifications ALTER COLUMN ctx_source SET NOT NULL;
ALTER TABLE teacher_notifications ADD CONSTRAINT teacher_notifications_ctx_source_check
    CHECK (ctx_source = 'course-map'::text OR ctx_source = 'class-activity'::text);
ALTER TABLE teacher_notifications DROP CONSTRAINT teacher_notifications_current_item_type_check;
ALTER TABLE teacher_notifications ADD CONSTRAINT teacher_notifications_current_item_type_check
    CHECK (current_item_type::text = ANY (ARRAY['collection'::text, 'assessment'::text,
       'collection-external'::text, 'assessment-external'::text, 'offline-activity'::text ]));


ALTER TABLE student_notifications ADD COLUMN milestone_id TEXT;
ALTER TABLE student_notifications ADD COLUMN ctx_source TEXT DEFAULT 'course-map';
UPDATE student_notifications SET ctx_source = 'course-map';
ALTER TABLE student_notifications ALTER COLUMN ctx_source SET NOT NULL;
ALTER TABLE student_notifications ADD CONSTRAINT student_notifications_ctx_source_check
    CHECK (ctx_source = 'course-map'::text OR ctx_source = 'class-activity'::text);
ALTER TABLE student_notifications DROP CONSTRAINT student_notifications_current_item_type_check;
ALTER TABLE student_notifications ADD CONSTRAINT student_notifications_current_item_type_check
    CHECK (current_item_type::text = ANY (ARRAY['collection'::text, 'assessment'::text,
       'collection-external'::text, 'assessment-external'::text, 'offline-activity'::text ]));

