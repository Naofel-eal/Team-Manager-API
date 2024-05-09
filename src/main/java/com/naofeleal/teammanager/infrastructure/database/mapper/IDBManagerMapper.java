package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.model.DBManager;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {IDBTeamMapper.class}
)
public interface IDBManagerMapper {
    default String nameToString(Name name) {return name == null ? "" : name.toString();}
    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.firstname", target = "firstname")
    @Mapping(source = "user.lastname", target = "lastname")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.password", target = "password")
    @Mapping(target = "team.manager", ignore = true)
    Manager toDomainModel(DBManager dbManager);

    @Mapping(source = "id", target = "user.id")
    @Mapping(source = "firstname", target = "user.firstname")
    @Mapping(source = "lastname", target = "user.lastname")
    @Mapping(source = "email", target = "user.email")
    @Mapping(source = "password", target = "user.password")
    @Mapping(target = "team.manager", ignore = true)
    DBManager fromDomainModel(Manager manager);
}
