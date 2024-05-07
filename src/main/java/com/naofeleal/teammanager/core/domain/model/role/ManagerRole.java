package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;

public class ManagerRole extends SimpleUserRole {
    public ManagerRole() {
        super();
    }

    @Override
    protected void addDefaultPermissions() {
        super.addDefaultPermissions();
        permissions.add(new Permission(PermissionCode.CAN_ADD_TEAM_MEMBER));
        permissions.add(new Permission(PermissionCode.CAN_REMOVE_TEAM_MEMBER));
    }

    @Override
    public String getCode() {
        return RoleCode.MANAGER.toString();
    }
}
