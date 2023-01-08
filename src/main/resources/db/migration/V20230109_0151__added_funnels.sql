alter table tasks
    drop column if exists quotes;

alter table applications
    add column description text;

create table funnels
(
    id   bigserial
        constraint funnels_pkey
            primary key,
    name varchar(255) not null
);

alter table application_statuses
    add column funnel_id bigint
        references funnels (id)
            on update cascade
            on delete cascade;

create table departments_funnels
(
    department_id bigint
        references departments (id)
            on update cascade
            on delete cascade,
    funnels_id     bigint
        references funnels (id)
            on update cascade
            on delete cascade
);