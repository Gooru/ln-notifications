ALTER TABLE student_notifications 
    ALTER COLUMN ctx_course_id DROP NOT NULL,
    ALTER COLUMN ctx_unit_id DROP NOT NULL,
    ALTER COLUMN ctx_lesson_id DROP NOT NULL,
    ALTER COLUMN ctx_collection_id DROP NOT NULL;
    
ALTER TABLE student_notifications 
	ADD COLUMN ctx_ca_id bigint, 
	ADD COLUMN ctx_tx_code text,
	ADD COLUMN ctx_tx_code_type text;
	
ALTER TABLE student_notifications 
DROP CONSTRAINT student_notifications_ctx_source_check, 
ADD CONSTRAINT student_notifications_ctx_source_check CHECK (ctx_source::text = ANY(ARRAY['course-map'::text, 'class-activity'::text, 'proficiency'::text]));

CREATE UNIQUE INDEX sn_uccacppit_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_ca_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_source = 'class-activity';
    
CREATE UNIQUE INDEX sn_utxctppit_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_ca_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_source = 'proficiency';
    
ALTER TABLE student_notifications 
DROP CONSTRAINT student_notifications_ctx_path_type_check, 
ADD CONSTRAINT student_notifications_ctx_path_type_check CHECK (ctx_path_type::text = 
ANY(ARRAY['system'::text, 'teacher'::text, 'route0'::text, 'ca.system'::text, 'ca.teacher'::text, 'proficiency.system'::text, 'proficiency.teacher'::text]));


    