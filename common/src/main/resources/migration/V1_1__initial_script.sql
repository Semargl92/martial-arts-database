create table if not exists users
(
    id bigserial not null
        constraint users_pkey
            primary key,
    name varchar(200) not null,
    surname varchar(200) not null,
    login varchar(200),
    gender varchar(100) default 'NOT_SELECTED'::character varying not null,
    weight real,
    is_deleted boolean not null,
    created timestamp,
    changed timestamp,
    birth_date date,
    password varchar(200)
);

alter table users owner to postgres;

create unique index users_id_uindex
    on users (id);

create unique index if not exists users_login_uindex
    on users (login);

create index if not exists users_name_surname
    on users (name, surname);

create index if not exists users_surname
    on users (surname);

create index if not exists users_gender
    on users (gender);

create index users_password_index
    on users (password);

create table if not exists martial_arts
(
    id bigserial not null
        constraint martial_arts_pkey
            primary key,
    name varchar(100) not null,
    type varchar(100) default 'NOT_SELECTED'::character varying not null,
    origin varchar(100) not null,
    foundation_date date,
    description varchar(1000),
    weapon_technics_available boolean not null
);

alter table martial_arts owner to postgres;

create index if not exists martial_arts_name
    on martial_arts (name);

create index if not exists martial_arts_origin
    on martial_arts (origin);

create index if not exists martial_arts_type
    on martial_arts (type);

create table if not exists grades
(
    id bigserial not null
        constraint grades_pkey
            primary key,
    name varchar(100) not null,
    duration_for_next_lvl integer,
    number_of_trainings_days integer,
    exam_description varchar(2000),
    martial_art_id bigint not null
        constraint grades_martial_arts_id_fk
            references martial_arts
);

alter table grades owner to postgres;

create index if not exists grades_exam
    on grades (exam_description);

create index if not exists grades_name
    on grades (name);

create index if not exists grades_martial_art_id_index
    on grades (martial_art_id);

create table if not exists students
(
    id bigserial not null
        constraint students_pkey
            primary key,
    user_id bigint not null
        constraint students_users_id_fk
            references users,
    grade_id bigint not null
        constraint students_grades_id_fk
            references grades,
    teachers_user_id bigint,
    last_exam_date date,
    numbers_of_trainings_days bigint,
    start_date date,
    is_deleted boolean not null,
    created timestamp,
    changed timestamp
);

alter table students owner to postgres;

create index if not exists students_grades
    on students (grade_id);

create index if not exists students_teacher
    on students (teachers_user_id);

create index if not exists students_user_id
    on students (user_id);

create table if not exists exercises
(
    id bigserial not null
        constraint exercises_pkey
            primary key,
    name varchar(200) not null,
    grade_id bigint
        constraint exercises_grades_id_fk
            references grades,
    description varchar(2000),
    resources varchar(2000),
    type varchar(200) default 'NOT_SELECTED'::character varying not null,
    is_weapon_technik boolean not null
);

alter table exercises owner to postgres;

create index if not exists exercises_name
    on exercises (name);

create index if not exists exercises_type
    on exercises (type);

create index if not exists exercises_weapon
    on exercises (is_weapon_technik);

create table roles
(
    id serial not null
        constraint roles_pk
            primary key,
    role_name varchar(20) not null
);

alter table roles owner to postgres;

create unique index roles_role_name_uindex
    on roles (role_name);

create table if not exists user_roles
(
    id bigserial not null
        constraint user_roles_pk
            primary key,
    role_id integer not null
        constraint user_roles_roles_id_fk
            references roles
            on update cascade on delete cascade,
    user_id bigint not null
        constraint user_roles_users_id_fk
            references users
            on update cascade on delete cascade
);

alter table user_roles owner to postgres;

create index if not exists user_roles_role_id_index
    on user_roles (role_id);

create index if not exists user_roles_role_id_user_id_uindex
    on user_roles (role_id, user_id);

create index if not exists user_roles_user_id_index
    on user_roles (user_id);