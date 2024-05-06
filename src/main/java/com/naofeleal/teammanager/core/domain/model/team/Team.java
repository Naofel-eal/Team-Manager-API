package com.naofeleal.teammanager.core.domain.model.team;

import com.naofeleal.teammanager.core.domain.model.role.Manager;
import com.naofeleal.teammanager.core.domain.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public Manager manager;
    public List<User> members;

    public Team(Manager manager, List<User> members) {
        this.manager = manager;
        this.members = members;
    }

    public Team(Manager manager) {
        this.manager = manager;
        this.members = new ArrayList<>();
    }

    public Team() {}
}
