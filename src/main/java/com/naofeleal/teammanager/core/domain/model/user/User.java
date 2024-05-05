package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.Role;

public class User {
    public Name firstname;
    public Name lastname;
    public Email email;
    public Password password;
    public Role role;
    
    public User(Name firstname, Name lastname, Email email, Password password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User() {}

    public String getEmail() {
        return email.toString();
    }

    public String getPassword() {
        return password.toString();
    }
}
