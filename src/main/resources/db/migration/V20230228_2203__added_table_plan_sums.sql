create table plan_sums
(
    id            bigserial      not null
        constraint plan_sums_pkey
            primary key,
    sum           numeric(19, 2) not null,
    department_id bigint
        constraint fk9syjt7cfmjsklwpya0rbdaxda
            references departments
);

alter table plan_sums
    owner to postgres;

