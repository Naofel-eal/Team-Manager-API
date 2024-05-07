package com.naofeleal.teammanager.shared.mapper;

import com.naofeleal.teammanager.core.domain.model.role.*;
import com.naofeleal.teammanager.infrastructure.database.exception.UnknownRoleException;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IBaseRoleMapper {
    default BaseRole stringToRole(String code) {
        if (code.equals(RoleCode.SIMPLE_USER.toString()))
            return new SimpleUserRole();
        else if (code.equals(RoleCode.MANAGER.toString()))
            return new ManagerRole();
        else if (code.equals(RoleCode.ADMIN.toString()))
            return new AdminRole();
        else
            throw new UnknownRoleException(code);
    }

    default String roleToString(BaseRole role) {return role.getCode();}
}
