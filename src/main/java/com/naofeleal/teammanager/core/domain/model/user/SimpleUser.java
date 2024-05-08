package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.SimpleUserRole;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

import java.util.Objects;

public class SimpleUser extends BaseUser {
    public SimpleUser(Name firstName, Name lastName, Email email, Password password) {
        super(firstName, lastName, email, password, new SimpleUserRole());
    }

    public Manager upgradeToManager() {
        return new Manager(this);
    }

    public SimpleUser() {
        super();
        this.role = new SimpleUserRole();
    }
}
