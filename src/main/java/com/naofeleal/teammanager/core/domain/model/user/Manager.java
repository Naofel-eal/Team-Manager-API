package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.ManagerRole;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

public class Manager extends BaseUser {
    public Team team;

    public Manager(Name firstName, Name lastName, Email email, Password password) {
        super(firstName, lastName, email, password, new ManagerRole());
        this.team = new Team();
    }

    public Manager(SimpleUser simpleUser) {
        super(simpleUser);
        this.team = new Team();
    }

    public Manager() {
        super();
    }

    public void addMember(SimpleUser newMember) {
        this.team.addMember(newMember);
    }

    public void removeMember(SimpleUser member) {
        this.team.removeMember(member);
    }
}
