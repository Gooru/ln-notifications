-- drop table teacher_notifications_master
-- drop table student_notifications_master
-- drop table teacher_notifications
-- drop table student_notifications

create table teacher_notifications_master (
    name text PRIMARY KEY,
    description text,
    is_actionable boolean NOT NULL DEFAULT true,
    created_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    updated_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL
);

COMMENT on table teacher_notifications_master IS 'Master table to store notifications types and its metadata for teachers';

create table student_notifications_master (
    name text PRIMARY KEY,
    description text,
    is_actionable boolean NOT NULL DEFAULT true,
    created_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    updated_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL
);

COMMENT on table student_notifications_master IS 'Master table to store notifications types and its metadata for students';


create table teacher_notifications (
    id bigserial NOT NULL,
    ctx_class_id uuid NOT NULL,
    ctx_class_code text NOT NULL,
    ctx_course_id uuid NOT NULL,
    ctx_unit_id uuid NOT NULL,
    ctx_lesson_id uuid NOT NULL,
    ctx_collection_id uuid,
    current_item_id uuid NOT NULL,
    current_item_type text NOT NULL CHECK (current_item_type::text = ANY (ARRAY['collection'::text, 'assessment'::text,
        'collection-external'::text, 'assessment-external'::text ])),
    current_item_title text NOT NULL,
    notification_type text NOT NULL,
    ctx_path_id bigint,
    ctx_path_type text CHECK (ctx_path_type::text = ANY (ARRAY['system'::text, 'teacher'::text, 'route0'::text])),
    occurrence int NOT NULL DEFAULT 0,
    users text[] NOT NULL,
    created_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    updated_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    CONSTRAINT tn_pkey PRIMARY KEY (id),
    FOREIGN KEY (notification_type) references teacher_notifications_master (name)
);

ALTER TABLE teacher_notifications OWNER TO nucleus;

ALTER TABLE teacher_notifications ADD CONSTRAINT pi_pt_chk CHECK ((ctx_path_id is null AND ctx_path_type is NULL)
    OR (ctx_path_id is NOT NULL AND ctx_path_type is NOT NULL));

CREATE UNIQUE INDEX tn_cculcnitpp_unq_idx
    ON teacher_notifications (ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_path_id is not null and ctx_path_type is not null and ctx_collection_id is not null;

CREATE UNIQUE INDEX tn_cculcitn_unq_idx
    ON teacher_notifications (ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type)
    where ctx_path_id is null and ctx_path_type is null and ctx_collection_id is not null;

CREATE UNIQUE INDEX tn_cculcnitpp1_unq_idx
    ON teacher_notifications (ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_path_id is not null and ctx_path_type is not null and ctx_collection_id is null;

CREATE UNIQUE INDEX tn_cculcitn1_unq_idx
    ON teacher_notifications (ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id,
    current_item_id, current_item_type, notification_type)
    where ctx_path_id is null and ctx_path_type is null and ctx_collection_id is null;

CREATE INDEX tn_ua_idx ON teacher_notifications USING btree (updated_at);


COMMENT on table teacher_notifications IS 'Store the teacher notifications';

-- Student notifications

create table student_notifications (
    id bigserial NOT NULL,
    ctx_user_id uuid NOT NULL,
    ctx_class_id uuid NOT NULL,
    ctx_class_code text NOT NULL,
    ctx_course_id uuid NOT NULL,
    ctx_unit_id uuid NOT NULL,
    ctx_lesson_id uuid NOT NULL,
    ctx_collection_id uuid,
    current_item_id uuid NOT NULL,
    current_item_type text NOT NULL CHECK (current_item_type::text = ANY (ARRAY['collection'::text, 'assessment'::text,
        'collection-external'::text, 'assessment-external'::text ])),
    current_item_title text NOT NULL,
    notification_type text NOT NULL,
    ctx_path_id bigint,
    ctx_path_type text CHECK (ctx_path_type::text = ANY (ARRAY['system'::text, 'teacher'::text, 'route0'::text])),
    created_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    updated_at timestamp without time zone DEFAULT timezone('UTC'::text, now()) NOT NULL,
    CONSTRAINT sn_pkey PRIMARY KEY (id),
    FOREIGN KEY (notification_type) references student_notifications_master(name)
);

ALTER TABLE student_notifications OWNER TO nucleus;

ALTER TABLE student_notifications ADD CONSTRAINT pi_pt_chk CHECK ((ctx_path_id is null AND ctx_path_type is NULL)
    OR (ctx_path_id is NOT NULL AND ctx_path_type is NOT NULL));

CREATE UNIQUE INDEX sn_ucculcppit_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_path_id is not null and ctx_path_type is not null and ctx_collection_id is not null;

CREATE UNIQUE INDEX sn_ucculcit_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id, ctx_collection_id,
    current_item_id, current_item_type, notification_type)
    where ctx_path_id is null and ctx_path_type is null and ctx_collection_id is not null;

CREATE UNIQUE INDEX sn_ucculcppit1_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id,
    current_item_id, current_item_type, notification_type, ctx_path_id, ctx_path_type)
    where ctx_path_id is not null and ctx_path_type is not null and ctx_collection_id is null;

CREATE UNIQUE INDEX sn_ucculcit1_unq_idx
    ON student_notifications (ctx_user_id, ctx_class_id, ctx_course_id, ctx_unit_id, ctx_lesson_id,
    current_item_id, current_item_type, notification_type)
    where ctx_path_id is null and ctx_path_type is null and ctx_collection_id is null;

CREATE INDEX sn_ua_idx ON student_notifications USING btree (updated_at);

COMMENT on table student_notifications IS 'Store the student notifications';

-- Data insertions for master table
insert into teacher_notifications_master values
    ('student.self.report', null, false, now(), now()),
    ('student.gradable.submission', null, true, now(), now());

insert into student_notifications_master values
    ('teacher.override', null, false, now(), now()),
    ('teacher.grading.complete', null, false, now(), now()),
    ('teacher.suggestion', null, true, now(), now());

