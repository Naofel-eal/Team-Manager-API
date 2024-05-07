package com.naofeleal.teammanager.core.domain.model.team;

import com.naofeleal.teammanager.core.domain.model.role.ManagerRole;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public Manager manager;
    public List<BaseUser> members;

    public Team(Manager manager, List<BaseUser> members) {
        this.manager = manager;
        this.members = members;
    }

    public Team(Manager manager) {
        this.manager = manager;
        this.members = new ArrayList<>();
    }

    public Team() {}
}
