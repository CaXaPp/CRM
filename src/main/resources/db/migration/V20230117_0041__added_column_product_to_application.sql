alter table products
    add column department_id bigint
        references departments (id)
            on update cascade
            on delete cascade;