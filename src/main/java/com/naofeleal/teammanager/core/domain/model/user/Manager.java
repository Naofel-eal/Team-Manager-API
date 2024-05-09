package com.naofeleal.teammanager.core.domain.model.user;

import com.naofeleal.teammanager.core.domain.model.role.ManagerRole;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;

public class Manager extends BaseUser {
    public Team team;

    public Manager(BaseUser user, Team team) {
        super(user);
        this.team = team;
    }

    public Manager(Long id, Name firstName, Name lastName, Email email, Password password, Team team) {
        super(id, firstName, lastName, email, password, new ManagerRole());
        this.team = team;
    }

    public Manager(Name firstName, Name lastName, Email email, Password password, Team team) {
        super(firstName, lastName, email, password, new ManagerRole());
        this.team = team;
    }

    public Manager(Name firstName, Name lastName, Email email, Password password) {
        super(firstName, lastName, email, password, new ManagerRole());
    }

    public Manager(SimpleUser simpleUser) {
        super(simpleUser.id, simpleUser.firstname, simpleUser.lastname, simpleUser.email, simpleUser.password, new ManagerRole());
        this.team = new Team(this);
    }

    public Manager() {
        super();
        this.role = new ManagerRole();
    }
}
