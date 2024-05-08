package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;

public class SimpleUserRole extends BaseRole {
    public SimpleUserRole() {
        super();

    }

    @Override
    protected void addDefaultPermissions() {
        permissions.add(new Permission(PermissionCode.CAN_CONSULT_TEAMS));
        permissions.add(new Permission(PermissionCode.CAN_CONSULT_TEAM_MEMBERS));
    }

    @Override
    public String getCode() {
        return RoleCode.SIMPLE_USER.toString();
    }
}
