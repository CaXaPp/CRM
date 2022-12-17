INSERT INTO public.departments (name) VALUES ('Восточный отдел');
INSERT INTO public.departments (name) VALUES ('Западный отдел');
INSERT INTO public.departments (name) VALUES ('Северный отдел');
INSERT INTO public.departments (name) VALUES ('Южный отдел');
INSERT INTO public.user_statuses (name, value) VALUES ('Active', 'Активен');
INSERT INTO public.user_statuses (name, value) VALUES ('Fired', 'Уволен');
INSERT INTO public.client_sources (name) VALUES ('Инстаграм');
INSERT INTO public.client_sources (name) VALUES ('Facebook');
INSERT INTO public.client_sources (name) VALUES ('Друзья');
INSERT INTO public.client_sources (name) VALUES ('Телевизор');
INSERT INTO public.client_sources (name) VALUES ('Газета');
INSERT INTO public.client_sources (name) VALUES ('Telegram');
INSERT INTO public.client_sources (name) VALUES ('WhatsApp');
INSERT INTO public.client_sources (name) VALUES ('Другое');
INSERT INTO public.application_statuses (name) VALUES ('Новое');
INSERT INTO public.application_statuses (name) VALUES ('На обслуживании');
INSERT INTO public.application_statuses (name) VALUES ('Переговоры');
INSERT INTO public.application_statuses (name) VALUES ('Отказ');
INSERT INTO public.application_statuses (name) VALUES ('Успешно');
INSERT INTO public.application_statuses (name) VALUES ('Принятие решения');
INSERT INTO public.roles (name, value) VALUES ('Администратор', 'ROLE_ADMIN');
INSERT INTO public.roles (name, value) VALUES ('Сотрудник', 'ROLE_EMPLOYEE');
INSERT INTO public.roles (name, value) VALUES ('Руководитель', 'ROLE_MANAGER');
INSERT INTO public.task_types (name) VALUES ('Связаться');
INSERT INTO public.task_types (name) VALUES ('Встретиться');
INSERT INTO public.task_types (name) VALUES ('Другое');
INSERT INTO public.products (name) VALUES ('Страхование жилья');
INSERT INTO public.products (name) VALUES ('Страхование автомобиля');
INSERT INTO public.products (name) VALUES ('Страхование жизни');
INSERT INTO public.products (name) VALUES ('Страхование компании');