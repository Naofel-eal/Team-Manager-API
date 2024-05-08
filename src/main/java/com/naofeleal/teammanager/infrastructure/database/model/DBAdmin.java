package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id")
public class DBAdmin extends DBBaseUser {
    public DBAdmin(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }

    public DBAdmin() {}
}
