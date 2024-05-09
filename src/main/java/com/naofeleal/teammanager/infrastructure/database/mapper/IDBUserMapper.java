package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;

import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {IBaseRoleMapper.class, IDBTeamMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDBUserMapper extends IGenericMapper<SimpleUser, DBUser> {
    default String nameToString(Name name) {return name == null ? "" : name.toString();}

    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}
}
