package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;

import java.util.HashSet;
import java.util.Set;

public class SimpleUserRole extends BaseRole {
    public SimpleUserRole() {
        super();
    }

    public Set<Permission> getPermissions() {
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(PermissionCode.CAN_CONSULT_TEAMS));
        permissions.add(new Permission(PermissionCode.CAN_CONSULT_TEAM_MEMBERS));
        return permissions;
    }

    @Override
    public String getCode() {
        return RoleCode.SIMPLE_USER.toString();
    }
}
