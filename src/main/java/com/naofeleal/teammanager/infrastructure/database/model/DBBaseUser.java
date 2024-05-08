package com.naofeleal.teammanager.infrastructure.database.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@MappedSuperclass
public abstract class DBBaseUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String firstname;
    public String lastname;

    @Column(unique = true)
    public String email;
    public String password;

    public DBBaseUser(String firstname, String lastname, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public DBBaseUser() {}

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
        DBBaseUser dbBaseUser = (DBBaseUser) o;
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
