package com.naofeleal.teammanager.core.domain.model.role;

import com.naofeleal.teammanager.core.domain.model.permission.Permission;
import com.naofeleal.teammanager.core.domain.model.permission.PermissionCode;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class BaseRole {
    public BaseRole() {}

    public boolean hasPermission(PermissionCode permissionCode) {
        for (Permission permission : this.getPermissions()) {
            System.out.println(permission.code);
        }
        return getPermissions().stream().anyMatch(permission -> permission.code.equals(permissionCode));
    }

    public abstract Set<Permission> getPermissions();
    public abstract String getCode();

    @Override
    public String toString() {
        return getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseRole that = (BaseRole) o;
        return getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
