

INSERT INTO public.api_authorities (id, version, created_at, updated_at, description, name) VALUES (NEXTVAL('authority_api_seq') , 0, '2019-07-31 20:45:36.671000', '2019-07-31 20:45:36.671000', 'User role', 'ROLE_USER');
INSERT INTO public.api_authorities (id, version, created_at, updated_at, description, name) VALUES(NEXTVAL('authority_api_seq') , 0, '2019-07-31 20:45:36.671000', '2019-07-31 20:45:36.671000', 'Admin role', 'ROLE_ADMIN');


INSERT INTO public.api_users (id, version, created_at, updated_at, email, enable, first_name, last_name, last_password_reset_date, password, username) VALUES (NEXTVAL('user_api_seq'), 0, '2019-07-31 20:45:36.718000', '2019-07-31 20:45:36.718000', 'user@api.api', true, 'user', 'user', '2019-07-31 20:45:36.718000', '$2a$10$KBpgf/t1bxDd5Y0I.Ucrr.x4V2YLphXrmgujCyaTqbekqc2pD.E5q', 'user');
INSERT INTO public.api_users (id, version, created_at, updated_at, email, enable, first_name, last_name, last_password_reset_date, password, username) VALUES (NEXTVAL('user_api_seq') , 0, '2019-07-31 20:45:36.734000', '2019-07-31 20:45:36.734000', 'admin@api.api', true, 'admin', 'admin', '2019-07-31 20:45:36.734000', '$2a$10$Oa9ZCraZ9dNn174f6E4lAeFEnIzkTFb4m0D1ivUFXkSaaHPLKXEqu', 'admin');


INSERT INTO public.api_users_authorities (authority_id, user_id) VALUES (1, 1);
INSERT INTO public.api_users_authorities (authority_id, user_id) VALUES (1, 2);
INSERT INTO public.api_users_authorities (authority_id, user_id) VALUES (2, 2);
