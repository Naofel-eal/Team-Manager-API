CREATE TABLE public.team_user (
  team_id BIGSERIAL,
  user_id BIGINT NOT NULL,
  CONSTRAINT team_user_pkey PRIMARY KEY (user_id, team_id)
);

-- public.team_user foreign keys

ALTER TABLE public.team_user ADD CONSTRAINT FK_team_user_user FOREIGN KEY (user_id) REFERENCES public.users(id) ON DELETE CASCADE;
ALTER TABLE public.team_user ADD CONSTRAINT FK_team_user_team FOREIGN KEY (team_id) REFERENCES public.team(id) ON DELETE CASCADE;