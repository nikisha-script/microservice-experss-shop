insert into "auth-service"."user_profile"
(id_customer, push_notification, password_encoded, email,created, updated) values
(gen_random_uuid(), false, '$2a$12$7pe1GswOJVPQB4RqoE/kC.jDLKlRC3ZaZ2FsUOS/77Cl/ipLABB3C', 'admin@mail.ru', now(), now());