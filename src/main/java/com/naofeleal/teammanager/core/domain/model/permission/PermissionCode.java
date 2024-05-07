package com.naofeleal.teammanager.core.domain.model.permission;

public enum PermissionCode {
    // Permissions for simple users
    CAN_CONSULT_TEAMS("CAN_CONSULT_TEAMS"),
    CAN_CONSULT_TEAM_MEMBERS("CAN_CONSULT_TEAM_MEMBERS"),

    // Permissions for Managers
    CAN_ADD_TEAM_MEMBER("CAN_ADD_TEAM_MEMBER"),
    CAN_REMOVE_TEAM_MEMBER("CAN_REMOVE_TEAM_MEMBER"),

    // Permissions for Admins
    CAN_ADD_ANY_TEAM_MEMBER("CAN_ADD_ANY_TEAM_MEMBER"),
    CAN_REMOVE_ANY_TEAM_MEMBER("CAN_REMOVE_ANY_TEAM_MEMBER"),
    CAN_CREATE_TEAM("CAN_CREATE_TEAM"),
    CAN_DELETE_TEAM("CAN_DELETE_TEAM"),
    CAN_CREATE_USER_ACCOUNT("CAN_CREATE_USER_ACCOUNT"),
    CAN_DELETE_USER_ACCOUNT("CAN_DELETE_USER_ACCOUNT");

    private final String permissionCode;

    PermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    @Override
    public String toString() {
        return permissionCode;
    }
}
