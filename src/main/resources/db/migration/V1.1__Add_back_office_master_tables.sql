------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS departments
(
    id         bigint PRIMARY KEY,
    display_id varchar(255)          NOT NULL,
    name       varchar(255)          NOT NULL,
    is_deleted boolean DEFAULT FALSE NOT NULL
);

CREATE UNIQUE index unique_department_display_id ON departments (display_id)
    WHERE is_deleted = FALSE;

CREATE UNIQUE index unique_department_name ON departments (name)
    WHERE is_deleted = FALSE;

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS roles
(
    id            bigint PRIMARY KEY,
    display_id    varchar(255)          NOT NULL,
    department_id bigint                NOT NULL,
    name          varchar(255)          NOT NULL,
    is_deleted    boolean DEFAULT FALSE NOT NULL,

    CONSTRAINT reference_department_id FOREIGN key (department_id) REFERENCES departments (id)
);

CREATE UNIQUE index unique_role_display_id ON roles (display_id, department_id)
    WHERE is_deleted = FALSE;

CREATE UNIQUE index unique_department_role_name ON roles (name, department_id)
    WHERE is_deleted = FALSE;

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS employees
(
    id              UUID PRIMARY KEY,
    department_id   bigint                NOT NULL,
    display_id      varchar(255)          NOT NULL,
    full_name       varchar(255)          NOT NULL,
    email           varchar(255)          NOT NULL,
    phone_number    varchar(255),
    hashed_password varchar(255)          NOT NULL,
    status          varchar(255)          NOT NULL,
    is_deleted      boolean DEFAULT FALSE NOT NULL,

    CONSTRAINT reference_department_id FOREIGN key (department_id) REFERENCES departments (id)
);

CREATE UNIQUE index unique_employee_display_id ON employees (display_id)
    WHERE is_deleted = FALSE;

CREATE UNIQUE index unique_employee_email ON employees (email)
    WHERE is_deleted = FALSE;

------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS employee_roles
(
    employee_id UUID   NOT NULL,
    role_id     bigint NOT NULL,

    PRIMARY KEY (employee_id, role_id),
    CONSTRAINT reference_employee_id FOREIGN key (employee_id) REFERENCES employees (id),
    CONSTRAINT reference_role_id FOREIGN key (role_id) REFERENCES roles (id)
);
