package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;

@Entity
@Table(name = "manager")
@PrimaryKeyJoinColumn(name = "id")
public class DBManager extends DBBaseUser{
    @OneToOne(mappedBy = "manager")
    public DBTeam team;

    public DBManager(String firstname, String lastname, String email, String password, DBTeam team) {
        super(firstname, lastname, email, password);
        this.team = team;
    }

    public DBManager(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }

    public DBManager() {}
}
