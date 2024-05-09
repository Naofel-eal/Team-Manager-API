package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;

import java.util.HashSet;
import java.util.Set;

import static com.naofeleal.teammanager.core.domain.model.permission.PermissionCode.*;

public class AdminRole extends SimpleUserRole {
    public AdminRole() {
        super();
    }

    public Set<Permission> getPermissions() {
        Set<Permission> permissions = new HashSet<>();
        permissions.addAll(super.getPermissions());
        permissions.add(new Permission(CAN_ADD_ANY_TEAM_MEMBER));
        permissions.add(new Permission(CAN_REMOVE_ANY_TEAM_MEMBER));
        permissions.add(new Permission(CAN_CREATE_TEAM));
        permissions.add(new Permission(CAN_DELETE_TEAM));
        permissions.add(new Permission(CAN_CREATE_USER_ACCOUNT));
        permissions.add(new Permission(CAN_DELETE_USER_ACCOUNT));
        return permissions;
    }


    @Override
    public String getCode() {
        return RoleCode.ADMIN.toString();
    }
}
