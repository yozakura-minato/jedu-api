------------------------------------------------------------------------------------------------------------------------
create table if not exists students
(
    id              uuid    default uuidv7() primary key,

    display_id      varchar(255)          not null,
    full_name       varchar(255)          not null,
    email           varchar(255)          not null,
    phone_number    varchar(255),
    hashed_password varchar(255)          not null,
    status          varchar(255)          not null,

    created_at      timestamp             not null,
    created_by      uuid                  not null,
    updated_at      timestamp,
    updated_by      uuid,
    is_deleted      boolean default false not null,
    deleted_at      timestamp,
    deleted_by      uuid,

    constraint completed_update_audit_fields
        check ((updated_at is null) = (updated_by is null)),
    constraint completed_delete_audit_fields
        check (
            (is_deleted = true and deleted_at is not null and deleted_by is not null)
                or
            (is_deleted = false and deleted_at is null and deleted_by is null)
            )
);

create unique index unique_student_display_id
    on students (display_id) where is_deleted = false;
create unique index unique_student_email
    on students (email) where is_deleted = false;
