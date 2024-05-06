package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.user.User;

public class Admin extends Manager {
    public Admin(User user) {
        super(user);
        this.role = RoleEnum.ADMIN;
    }
}
