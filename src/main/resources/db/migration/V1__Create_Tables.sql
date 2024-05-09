CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       firstname VARCHAR(100) NOT NULL,
                       lastname VARCHAR(100) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE team (
                      id SERIAL PRIMARY KEY
);

CREATE TABLE manager (
                         id SERIAL PRIMARY KEY,
                         user_id INT NOT NULL,
                         team_id INT NOT NULL,
                         CONSTRAINT fk_manager_user FOREIGN KEY (user_id)
                             REFERENCES users (id)
                             ON DELETE CASCADE,
                         CONSTRAINT fk_manager_team FOREIGN KEY (team_id)
                             REFERENCES team (id)
                             ON DELETE CASCADE,
                         CONSTRAINT unique_manager_team_user UNIQUE (user_id, team_id)
);

CREATE TABLE admin (
                         user_id INT NOT NULL,
                         CONSTRAINT fk_manager_user FOREIGN KEY (user_id)
                             REFERENCES users (id)
                             ON DELETE CASCADE,
                         PRIMARY KEY (user_id)
);

CREATE TABLE team_user (
                           team_id INT NOT NULL,
                           user_id INT NOT NULL,
                           CONSTRAINT fk_team_user_team FOREIGN KEY (team_id)
                               REFERENCES team (id)
                               ON DELETE CASCADE,
                           CONSTRAINT fk_team_user_user FOREIGN KEY (user_id)
                               REFERENCES users (id)
                               ON DELETE CASCADE,
                           PRIMARY KEY (team_id, user_id)
);

CREATE INDEX idx_team_user_team ON team_user (team_id);
CREATE INDEX idx_team_user_user ON team_user (user_id);
