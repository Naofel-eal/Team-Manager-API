package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.AdminRole;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

public class Admin extends BaseUser {
    public Admin(Name firstName, Name lastName, Email email, Password password) {
        super(firstName, lastName, email, password, new AdminRole());
    }

    public Admin() {
        super();
        this.role = new AdminRole();
    }
}
