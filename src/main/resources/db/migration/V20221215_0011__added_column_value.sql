alter table roles
add column value varchar(255) not null default '';

alter table user_statuses
add column value varchar(255) not null default '';