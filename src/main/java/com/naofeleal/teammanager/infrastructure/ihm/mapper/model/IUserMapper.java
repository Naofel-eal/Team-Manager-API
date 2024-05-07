package com.naofeleal.teammanager.infrastructure.ihm.mapper.model;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.ihm.model.user.UserDTO;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = IBaseRoleMapper.class
)
public interface IUserMapper {
    UserDTO toDTO(BaseUser user);
    List<UserDTO> toDTO(List<BaseUser> users);

    default String nameToString(Name name) {return name == null ? null : name.toString();}
    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}
}
