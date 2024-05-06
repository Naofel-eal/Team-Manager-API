package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.User;

public class Manager extends User {
    public Team team;

    public Manager(User user) {
        super(user);
        this.role = RoleEnum.MANAGER;
    }

    public Manager() {}
}
