package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "team")
public class DBTeam {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @OneToOne
    public DBManager manager;

    @OneToMany(mappedBy = "team")
    public List<DBSimpleUser> members;

    public DBTeam() {}

    public DBTeam(Long id, DBManager manager, List<DBSimpleUser> members) {
        this.id = id;
        this.manager = manager;
        this.members = members;
    }

    public DBTeam(DBManager manager, List<DBSimpleUser> members) {
        this.manager = manager;
        this.members = members;
    }
}
