------------------------------------------------------------------------------------------------------------------------
create table if not exists employees
(
    id              uuid primary key,

    display_id      varchar(255)          not null,
    full_name       varchar(255)          not null,
    email           varchar(255)          not null,
    phone_number    varchar(255),
    hashed_password varchar(255)          not null,
    status          varchar(255)          not null,

    is_deleted      boolean default false not null
);

create unique index unique_employee_display_id
    on employees (display_id) where is_deleted = false;
create unique index unique_employee_email
    on employees (email) where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists departments
(
    id         bigint primary key,

    display_id varchar(255)          not null,
    name       varchar(255)          not null,

    is_deleted boolean default false not null
);

create unique index unique_department_display_id
    on departments (display_id) where is_deleted = false;
create unique index unique_department_name
    on departments (name) where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists employee_departments
(
    employee_id   uuid   not null,
    department_id bigint not null,

    primary key (employee_id, department_id),
    constraint reference_employee_id
        foreign key (employee_id) references employees (id),
    constraint reference_department_id
        foreign key (department_id) references departments (id)
);

------------------------------------------------------------------------------------------------------------------------
create table if not exists roles
(
    id            bigint primary key,
    department_id bigint                not null,

    display_id    varchar(255)          not null,
    name          varchar(255)          not null,

    is_deleted    boolean default false not null,

    constraint reference_department_id
        foreign key (department_id) references departments (id)
);

create unique index unique_department_role_display_id
    on roles (display_id) where is_deleted = false;
create unique index unique_department_role_name
    on roles (name) where is_deleted = false;

------------------------------------------------------------------------------------------------------------------------
create table if not exists employee_roles
(
    employee_id uuid   not null,
    role_id     bigint not null,

    primary key (employee_id, role_id),
    constraint reference_employee_id
        foreign key (employee_id) references employees (id),
    constraint reference_role_id
        foreign key (role_id) references roles (id)
);
