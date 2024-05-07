package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.role.BaseRole;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.Admin;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.exception.UnknownRoleException;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = IBaseRoleMapper.class,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDBUserMapper extends IGenericMapper<BaseUser, DBUser> {
    default String nameToString(Name name) {return name == null ? "" : name.toString();}
    default String emailToString(Email email) {return email == null ? "" : email.toString();}
    default String passwordToString(Password password) {return password == null ? "" : password.toString();}

    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}

    @Override
    default BaseUser toDomainModel(DBUser dbUser) {
        Name firstname = nameFromString(dbUser.firstname);
        Name lastname = nameFromString(dbUser.lastname);
        Email email = emailFromString(dbUser.email);
        Password password = passwordFromString(dbUser.password);
        String role = dbUser.role;

        if (RoleCode.SIMPLE_USER.toString().equals(role))
            return new SimpleUser(firstname, lastname, email, password);
        else if (RoleCode.MANAGER.toString().equals(role))
            return new Manager(firstname, lastname, email, password);
        else if (RoleCode.ADMIN.toString().equals(role))
            return new Admin(firstname, lastname, email, password);
        else
            throw new UnknownRoleException(role);
    }
}
