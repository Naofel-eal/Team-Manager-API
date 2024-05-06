package com.naofeleal.teammanager.infrastructure.ihm.mapper.model;

import com.naofeleal.teammanager.core.domain.model.role.RoleEnum;
import com.naofeleal.teammanager.core.domain.model.user.Email;
import com.naofeleal.teammanager.core.domain.model.user.Name;
import com.naofeleal.teammanager.core.domain.model.user.Password;
import com.naofeleal.teammanager.core.domain.model.user.User;
import com.naofeleal.teammanager.infrastructure.ihm.model.user.UserDTO;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IUserMapper extends IGenericMapper<User, UserDTO> {
    default String roleToString(RoleEnum role) {
        return role == null ? "" : role.toString();
    }
    default String nameToString(Name name) {return name == null ? "" : name.toString();}
    default String emailToString(Email email) {return email == null ? "" : email.toString();}
    default String passwordToString(Password password) {return password == null ? "" : password.toString();}

    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}
}
