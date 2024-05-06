package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.RoleEnum;

public class User {
    public Name firstname;
    public Name lastname;
    public Email email;
    public Password password;
    public RoleEnum role;

    public User(Name firstname, Name lastname, Email email, Password password, RoleEnum role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(Name firstname, Name lastname, Email email, Password password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = RoleEnum.USER;
    }

    public User(User user) {
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;
        this.password = user.password;
        this.role = user.role;
    }

    public User() {}

    public String getEmail() {
        return email.toString();
    }

    public String getPassword() {
        return password.toString();
    }
}
