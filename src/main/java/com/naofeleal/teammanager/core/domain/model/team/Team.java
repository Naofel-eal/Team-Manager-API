package com.naofeleal.teammanager.core.domain.model.team;

import com.naofeleal.teammanager.core.domain.exception.team.UserAlreadyMemberException;
import com.naofeleal.teammanager.core.domain.exception.team.UserNotMemberException;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Team {
    public Long id;
    public Manager manager;
    public Set<SimpleUser> members = new HashSet<>();

    public Team(Long id, Manager manager, List<SimpleUser> members) {
        this.id = id;
        this.manager = manager;
        this.members.addAll(members);
    }

    public Team(Manager manager, List<SimpleUser> members) {
        this.manager = manager;
        this.members.addAll(members);
    }

    public Team(Manager manager) {
        this.manager = manager;
    }

    public Team() {}

    public void addMember(SimpleUser member) {
        if(members.contains(member)) {
            throw new UserAlreadyMemberException(member.getEmail(), manager.getEmail());
        }
        members.add(member);
    }

    public void removeMember(SimpleUser member) {
        if(!members.contains(member)) {
            throw new UserNotMemberException(member.getEmail(), manager.getEmail());
        }
        members.remove(member);
    }
}
