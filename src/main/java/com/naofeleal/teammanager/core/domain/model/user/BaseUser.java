package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.role.BaseRole;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

import java.util.Objects;

public abstract class BaseUser {
    public Long id;
    public Name firstname;
    public Name lastname;
    public Email email;
    public Password password;
    public BaseRole role;

    public BaseUser(Long id, Name firstname, Name lastname, Email email, Password password, BaseRole role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public BaseUser(Name firstname, Name lastname, Email email, Password password, BaseRole role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public BaseUser(BaseUser user) {
        this.id = user.id;
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

    public String getRole() {return role.toString();}

    public boolean hasPermission(PermissionCode permissionCode) {return role.hasPermission(permissionCode);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseUser that = (BaseUser) o;

        return Objects.equals(id, that.id) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            firstname,
            lastname,
            email,
            password,
            role);
    }
}
