package com.naofeleal.teammanager.core.domain.model.team;

import com.naofeleal.teammanager.core.domain.exception.team.UserAlreadyMemberException;
import com.naofeleal.teammanager.core.domain.exception.team.UserNotMemberException;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;

import java.util.ArrayList;
import java.util.List;

public class Team {
    public Manager manager;
    public List<BaseUser> members;

    public Team(Manager manager, List<SimpleUser> members) {
        this.manager = manager;
        this.members = new ArrayList<BaseUser>();
        this.members.addAll(members);
        this.members.add(manager);
    }

    public Team(Manager manager) {
        this.manager = manager;
        this.members = new ArrayList<>(List.of(manager));
    }

    public Team() {}

    public void addMember(BaseUser member) {
        if(members.contains(member)) {
            throw new UserAlreadyMemberException(member.getEmail(), manager.getEmail());
        }
        members.add(member);
    }

    public void removeMember(BaseUser member) {
        if(!members.contains(member)) {
            throw new UserNotMemberException(member.getEmail(), manager.getEmail());
        }
        members.remove(member);
    }
}
