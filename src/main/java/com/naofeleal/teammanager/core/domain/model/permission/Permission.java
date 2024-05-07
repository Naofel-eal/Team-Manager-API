package com.naofeleal.teammanager.core.domain.model.permission;

public class Permission {
    public  PermissionCode code;

    public Permission(PermissionCode code) {
        this.code = code;
    }

    public Permission() {}

    @Override
    public String toString() {
        return code.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Permission permission))
            return false;
        return permission.code.equals(this.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
