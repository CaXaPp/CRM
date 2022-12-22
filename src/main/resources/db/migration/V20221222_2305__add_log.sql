create table logs
(
    id             bigserial,
    date           timestamp,
    description    text,
    user_id        bigint
        constraint usr_id
            references users (id),
    application_status_id bigint
        constraint foreign_key_name
            references application_statuses ("id"),
    constraint logs_id
        primary key (id)
);

