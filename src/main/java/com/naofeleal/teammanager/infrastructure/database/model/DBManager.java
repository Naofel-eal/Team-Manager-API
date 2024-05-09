package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "manager")
public class DBManager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    public DBUser user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    public DBTeam team;

    public DBManager(DBUser user, DBTeam team) {
        this.user = user;
        this.team = team;
    }

    public DBManager() {}
}
