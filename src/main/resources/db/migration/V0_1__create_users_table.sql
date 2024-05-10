CREATE TABLE public.users (
                              id BIGSERIAL PRIMARY KEY,
                              email varchar(255) NOT NULL,
                              firstname varchar(255) NOT NULL,
                              lastname varchar(255) NOT NULL,
                              "password" varchar(255) NOT NULL,
                              CONSTRAINT users_email_key UNIQUE (email)
);

CREATE INDEX idx_users_email ON public.users (email);
