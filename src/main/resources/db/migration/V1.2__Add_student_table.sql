------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS students
(
    id              UUID    DEFAULT uuidv7() PRIMARY KEY,

    display_id      varchar(255)          NOT NULL,
    full_name       varchar(255)          NOT NULL,
    email           varchar(255)          NOT NULL,
    phone_number    varchar(255),
    hashed_password varchar(255)          NOT NULL,
    status          varchar(255)          NOT NULL,

    created_at      timestamp             NOT NULL,
    created_by      UUID                  NOT NULL,
    updated_at      timestamp,
    updated_by      UUID,
    is_deleted      boolean DEFAULT FALSE NOT NULL,
    deleted_at      timestamp,
    deleted_by      UUID,

    CONSTRAINT completed_update_audit_fields CHECK ((updated_at IS NULL) = (updated_by IS NULL)),
    CONSTRAINT completed_delete_audit_fields
        CHECK ((is_deleted = TRUE AND deleted_at IS NOT NULL AND deleted_by IS NOT NULL)
            OR (is_deleted = FALSE AND deleted_at IS NULL AND deleted_by IS NULL))
);

CREATE UNIQUE index unique_student_display_id ON students (display_id)
    WHERE is_deleted = FALSE;

CREATE UNIQUE index unique_student_email ON students (email)
    WHERE is_deleted = FALSE;
