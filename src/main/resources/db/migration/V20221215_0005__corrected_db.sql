drop table if exists tasks;
drop table if exists users;
drop table if exists deals;
drop table if exists deal_statuses;
drop table if exists applications;

alter table products
    drop column price;

create table application_statuses
(
    id   bigserial    not null
        constraint application_statuses_pkey
            primary key,
    name varchar(255) not null
);

alter table application_statuses
    owner to postgres;



create table client_sources
(
    id   bigserial    not null
        constraint client_sources_pkey
            primary key,
    name varchar(255) not null
);

alter table client_sources
    owner to postgres;


create table roles
(
    id   bigserial    not null
        constraint roles_pkey
            primary key,
    name varchar(255) not null
);

alter table roles
    owner to postgres;

create table user_statuses
(
    id   bigserial    not null
        constraint user_statuses_pkey
            primary key,
    name varchar(255) not null
);

alter table user_statuses
    owner to postgres;


create table users
(
    id            bigserial    not null
        constraint users_pkey
            primary key,
    email         varchar(255) not null,
    enabled       boolean,
    first_name    varchar(255) not null,
    middle_name   varchar(255) not null,
    password      varchar(255) not null,
    surname       varchar(255) not null,
    department_id bigint
        constraint fksbg59w8q63i0oo53rlgvlcnjq
            references departments,
    role_id       bigint
        constraint fkp56c1712k691lhsyewcssf40f
            references roles,
    status_id     bigint
        constraint fk1lqs7qsjk62rrrt1rogyasqaf
            references user_statuses
);

alter table users
    owner to postgres;

create table applications
(
    id          bigserial    not null
        constraint applications_pkey
            primary key,
    address     varchar(255),
    company     varchar(255) not null,
    date        timestamp    not null,
    email       varchar(255) not null,
    name        varchar(255) not null,
    phone       varchar(255) not null,
    price       numeric(19, 2),
    employee_id bigint
        constraint fk8852j0o21s8oxanmt4aailbfc
            references users,
    product_id  bigint
        constraint fkic8fe8yml90uqtv6vcl8lje52
            references products,
    source_id   bigint
        constraint fkkddokuh6pjpn8601vm7uyk4k9
            references client_sources,
    status_id   bigint
        constraint fkg2x3n6mbkw06w118r7da36xw9
            references application_statuses
);

alter table applications
    owner to postgres;

create table tasks
(
    id             bigserial    not null
        constraint tasks_pkey
            primary key,
    created_at     timestamp    not null,
    deadline       timestamp    not null,
    name           varchar(255) not null,
    application_id bigint
        constraint fk1kc2rmeav04lohau1y70tm3pb
            references applications,
    employee_id    bigint
        constraint fkg6hs548hhpp8dera3e2a2e4rl
            references users,
    type_id        bigint
        constraint fkd3gkgnwhv6frcxvi0w2svc1da
            references task_types
);

alter table tasks
    owner to postgres;