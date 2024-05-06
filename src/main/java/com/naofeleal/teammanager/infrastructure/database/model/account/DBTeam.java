package com.naofeleal.teammanager.infrastructure.database.model.account;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
public class DBTeam {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @OneToOne
    public DBUser manager;

    @OneToMany
    public List<DBUser> members;

    public DBTeam() {}

    public DBTeam(Long id, DBUser manager, List<DBUser> members) {
        this.id = id;
        this.manager = manager;
        this.members = members;
    }

    public DBTeam(DBUser manager, List<DBUser> members) {
        this.manager = manager;
        this.members = members;
    }
}
