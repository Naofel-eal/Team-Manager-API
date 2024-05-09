package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;

import java.util.HashSet;
import java.util.Set;

public class ManagerRole extends SimpleUserRole {
    public ManagerRole() {
        super();
    }

    @Override
    public Set<Permission> getPermissions() {
        HashSet<Permission> permissions = new HashSet<>();
        permissions.addAll(super.getPermissions());
        permissions.add(new Permission(PermissionCode.CAN_ADD_TEAM_MEMBER));
        permissions.add(new Permission(PermissionCode.CAN_REMOVE_TEAM_MEMBER));
        return permissions;
    }

    @Override
    public String getCode() {
        return RoleCode.MANAGER.toString();
    }
}
