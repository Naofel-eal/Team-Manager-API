CREATE TABLE public."admin" (
    id BIGSERIAL,
    user_id BIGINT NULL,
    CONSTRAINT admin_pkey PRIMARY KEY (id),
    CONSTRAINT admin_user_id_key UNIQUE (user_id)
);

ALTER TABLE public."admin" ADD CONSTRAINT FK_admin_users FOREIGN KEY (user_id) REFERENCES public.users(id);