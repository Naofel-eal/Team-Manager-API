package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "simple_user")
@PrimaryKeyJoinColumn(name = "id")
public class DBSimpleUser extends DBBaseUser {
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    public DBTeam team;

    public DBSimpleUser(String firstname, String lastname, String email, String password) {
        super(firstname, lastname, email, password);
    }

    public DBSimpleUser() {}
}
