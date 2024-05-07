package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.BaseRole;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

public abstract class BaseUser {
    public Name firstname;
    public Name lastname;
    public Email email;
    public Password password;
    public BaseRole role;

    public BaseUser(Name firstname, Name lastname, Email email, Password password, BaseRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public BaseUser(BaseUser user) {
        this.firstname = user.firstname;
        this.lastname = user.lastname;
        this.email = user.email;
        this.password = user.password;
        this.role = user.role;
    }

    public BaseUser() {}

    public String getEmail() {
        return email.toString();
    }

    public String getPassword() {
        return password.toString();
    }
}
