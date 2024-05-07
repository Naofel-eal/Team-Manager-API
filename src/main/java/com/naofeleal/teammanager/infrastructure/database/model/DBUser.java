package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "_user")
public class DBUser implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;
    public String firstname;
    public String lastname;

    @Column(unique=true)
    public String email;
    public String password;

    public String role;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    public DBTeam team;

    public DBUser(String firstname, String lastname, String email, String password, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public DBUser() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
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
        DBUser dbUser = (DBUser) o;
        return Objects.equals(id, dbUser.id) && Objects.equals(email, dbUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
