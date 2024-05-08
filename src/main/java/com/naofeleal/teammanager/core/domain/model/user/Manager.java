package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.ManagerRole;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

public class Manager extends BaseUser {
    public Team team = new Team();

    public Manager(Name firstName, Name lastName, Email email, Password password, Team team) {
        super(firstName, lastName, email, password, new ManagerRole());
        this.team = team;
    }

    public Manager(Name firstName, Name lastName, Email email, Password password) {
        super(firstName, lastName, email, password, new ManagerRole());
    }

    public Manager(SimpleUser simpleUser) {
        super(simpleUser.firstname, simpleUser.lastname, simpleUser.email, simpleUser.password, new ManagerRole());
    }

    public Manager() {
        super();
        this.role = new ManagerRole();
    }

    public void addMember(SimpleUser newMember) {
        this.team.addMember(newMember);
    }

    public void removeMember(SimpleUser member) {
        this.team.removeMember(member);
    }

    public SimpleUser downgradeToSimpleUser() {
        return new SimpleUser(this.firstname, this.lastname, this.email, this.password);
    }
}
