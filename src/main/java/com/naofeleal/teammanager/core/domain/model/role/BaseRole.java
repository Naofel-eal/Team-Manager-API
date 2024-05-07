package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseRole {
    static protected Set<Permission> permissions;

    public BaseRole() {
        BaseRole.permissions = new HashSet<Permission>();
        this.addDefaultPermissions();
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public abstract String getCode();

    protected abstract void addDefaultPermissions();
}
