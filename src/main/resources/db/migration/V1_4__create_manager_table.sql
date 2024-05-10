CREATE TABLE public.manager (
    id BIGSERIAL,
    team_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT manager_pkey PRIMARY KEY (id),
    CONSTRAINT uk_14hic2rbgh72tpix5986m9as3 UNIQUE (team_id),
    CONSTRAINT uk_4vbgsjl6mcxrqyvts0hlilhob UNIQUE (user_id)
);

ALTER TABLE public.manager ADD CONSTRAINT FK_manager_team FOREIGN KEY (team_id) REFERENCES public.team(id) ON DELETE CASCADE;
ALTER TABLE public.manager ADD CONSTRAINT FK_manager_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;