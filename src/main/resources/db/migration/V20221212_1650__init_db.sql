create table deal_statuses
(
    id   bigserial    not null
        constraint deal_statuses_pkey
            primary key,
    name varchar(255) not null
);

alter table deal_statuses
    owner to postgres;

create table departments
(
    id   bigserial not null
        constraint departments_pkey
            primary key,
    name varchar(255)
);

alter table departments
    owner to postgres;

create table products
(
    id    bigserial      not null
        constraint products_pkey
            primary key,
    name  varchar(255)   not null,
    price numeric(19, 2) not null
);

alter table products
    owner to postgres;

create table applications
(
    id         bigserial    not null
        constraint applications_pkey
            primary key,
    company    varchar(255) not null,
    date       timestamp    not null,
    email      varchar(255) not null,
    name       varchar(255) not null,
    phone      varchar(255) not null,
    source     varchar(255) not null,
    product_id bigint
        constraint fkic8fe8yml90uqtv6vcl8lje52
            references products
);

alter table applications
    owner to postgres;

create table deals
(
    id         bigserial      not null
        constraint deals_pkey
            primary key,
    price      numeric(19, 2) not null,
    product_id bigint
        constraint fkdiyxm7qwacnjy62mr34jn35p6
            references products,
    status_id  bigint
        constraint fkhkmxt7aio1e30rd3spiaoaldc
            references deal_statuses
);

alter table deals
    owner to postgres;

create table task_types
(
    id   bigserial not null
        constraint task_types_pkey
            primary key,
    name varchar(255)
);

alter table task_types
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
    role          varchar(255) not null,
    status        varchar(255) not null,
    surname       varchar(255) not null,
    department_id bigint
        constraint fksbg59w8q63i0oo53rlgvlcnjq
            references departments
);

alter table users
    owner to postgres;

create table tasks
(
    id          bigserial    not null
        constraint tasks_pkey
            primary key,
    created_at  timestamp    not null,
    deadline    timestamp    not null,
    name        varchar(255) not null,
    deal_id     bigint
        constraint fkgqo43nga0j19y4hi0tcixaus1
            references deals,
    employee_id bigint
        constraint fkg6hs548hhpp8dera3e2a2e4rl
            references users,
    type_id     bigint
        constraint fkd3gkgnwhv6frcxvi0w2svc1da
            references task_types
);

alter table tasks
    owner to postgres;

