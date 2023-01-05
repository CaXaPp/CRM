alter table tasks
drop column name;

alter table tasks
add column quotes varchar(255),
add column result varchar(255);