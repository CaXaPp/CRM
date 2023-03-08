INSERT INTO public.departments (name) VALUES ('Отдел общего страхования');
INSERT INTO public.departments (name) VALUES ('Отдел страхования жизни');

INSERT INTO public.funnels (name) VALUES ('Воронка отдела общего страхования');
INSERT INTO public.funnels (name) VALUES ('Воронка отдела страхования жизни');

INSERT INTO public.user_statuses (name, value) VALUES ('Active', 'Активен');
INSERT INTO public.user_statuses (name, value) VALUES ('Fired', 'Уволен');

INSERT INTO public.client_sources (name) VALUES ('Инстаграм');
INSERT INTO public.client_sources (name) VALUES ('Facebook');
INSERT INTO public.client_sources (name) VALUES ('Друзья');
INSERT INTO public.client_sources (name) VALUES ('Телевизор');
INSERT INTO public.client_sources (name) VALUES ('Газета');
INSERT INTO public.client_sources (name) VALUES ('Telegram');
INSERT INTO public.client_sources (name) VALUES ('WhatsApp');
INSERT INTO public.client_sources (name) VALUES ('Сайт');
INSERT INTO public.client_sources (name) VALUES ('Сотрудник');
INSERT INTO public.client_sources (name) VALUES ('Другое');

INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Новое', 1);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('На обслуживании', 1);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Переговоры', 1);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Принятие решения', 1);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Отказ', 1);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Успешно', 1);

INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Новое', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('На обслуживании', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Проверка здоровья', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Анализ результатов проверки', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Принятие решения', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Отказ', 2);
INSERT INTO public.application_statuses (name, funnel_id) VALUES ('Успешно', 2);

INSERT INTO public.roles (name, value) VALUES ('Администратор', 'ROLE_ADMIN');
INSERT INTO public.roles (name, value) VALUES ('Сотрудник', 'ROLE_EMPLOYEE');
INSERT INTO public.roles (name, value) VALUES ('Руководитель', 'ROLE_MANAGER');

INSERT INTO public.task_types (name) VALUES ('Связаться');
INSERT INTO public.task_types (name) VALUES ('Встретиться');
INSERT INTO public.task_types (name) VALUES ('Другое');

INSERT INTO public.products (name, department_id) VALUES ('Страхование жилья', 1);
INSERT INTO public.products (name, department_id) VALUES ('Страхование автомобиля', 1);
INSERT INTO public.products (name, department_id) VALUES ('Страхование компании', 1);
INSERT INTO public.products (name, department_id) VALUES ('Страхование жизни', 2);

INSERT INTO public.departments_funnels (department_id, funnels_id) VALUES (1, 1);
INSERT INTO public.departments_funnels (department_id, funnels_id) VALUES (2, 2);

INSERT INTO public.users (email, enabled, first_name, middle_name, password, surname, department_id, role_id, status_id) VALUES ('user1@user.com', true, 'Сотрудник №1', 'Отчество', '$2a$10$V5Iy7/mVu94oaJAyXgKgxunVY7Vr4PljbSUfQGrI7r74k2eaJdlty', 'Фамилия', 1, 2, 1);
INSERT INTO public.users (email, enabled, first_name, middle_name, password, surname, department_id, role_id, status_id) VALUES ('user2@user.com', true, 'Сотрудник №2', 'Отчество', '$2a$10$P5bYbQxHa2keo2DxGTdvsOQQVjcdKCLpeP9qs8wCKrc6viVlWe0LW', 'Фамилия', 2, 2, 1);
INSERT INTO public.users (email, enabled, first_name, middle_name, password, surname, department_id, role_id, status_id) VALUES ('admin@admin.com', true, 'Админ', 'Отчество', '$2a$10$pa5MWgIvOkFwluTvR5Ize.7UfFW5tcMv.QsFXJ12ojQsCUcItKa8q', 'Фамилия', 1, 1, 1);
INSERT INTO public.users (email, enabled, first_name, middle_name, password, surname, department_id, role_id, status_id) VALUES ('manager@manager.com', true, 'Руководитель', 'Отчество', '$2a$10$0ZsKgr8//yWQvy7Pl8qYqOeyd5uBh9v1gBKB/nDCpkxGP.fq5RZge', 'Фамилия', 1, 3, 1);

INSERT INTO public.plan_sums (sum, department_id) VALUES (0.00, 1);
INSERT INTO public.plan_sums (sum, department_id) VALUES (0.00, 2);
