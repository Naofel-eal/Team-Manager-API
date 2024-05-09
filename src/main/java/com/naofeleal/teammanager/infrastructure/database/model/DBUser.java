package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class DBUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String firstname;
    public String lastname;

    @Column(unique = true)
    public String email;
    public String password;

    @ManyToOne
    @JoinTable(
            name = "team_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    public DBTeam team;

    public DBUser(Long id, String firstname, String lastname, String email, String password, DBTeam team) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.team = team;
    }

    public DBUser(Long id, String firstname, String lastname, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.team = null;
    }

    public DBUser(String firstname, String lastname, String email, String password, DBTeam team) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.team = team;
    }

    public DBUser() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBUser dbBaseUser = (DBUser) o;
        return Objects.equals(id, dbBaseUser.id)
                && Objects.equals(email, dbBaseUser.email)
                && Objects.equals(firstname, dbBaseUser.firstname)
                && Objects.equals(lastname, dbBaseUser.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstname, lastname);
    }
}
