package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.user.Admin;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.exception.UnknownRoleException;
import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBManager;
import com.naofeleal.teammanager.infrastructure.database.model.DBSimpleUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBAdmin;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {IBaseRoleMapper.class, IDBTeamMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDBUserMapper extends IGenericMapper<BaseUser, DBBaseUser> {
    IDBTeamMapper dbTeamMapper = Mappers.getMapper(IDBTeamMapper.class);

    default String nameToString(Name name) {return name == null ? "" : name.toString();}

    default Name nameFromString(String name) {return name == null ? null : new Name(name);}
    default Email emailFromString(String email) {return email == null ? null : new Email(email);}
    default Password passwordFromString(String password) {return password == null ? null : new Password(password);}

    @Override
    @Mapping(target = "team.manager", ignore = true)
    default BaseUser toDomainModel(DBBaseUser dbBaseUser) {
        Name firstname = nameFromString(dbBaseUser.firstname);
        Name lastname = nameFromString(dbBaseUser.lastname);
        Email email = emailFromString(dbBaseUser.email);
        Password password = passwordFromString(dbBaseUser.password);

        return switch (dbBaseUser) {
            case DBSimpleUser dbSimpleUser ->
                    new SimpleUser(firstname, lastname, email, password);
            case DBManager dbManager ->
                    new Manager(firstname, lastname, email, password, dbTeamMapper.toDomainModel(dbManager.team));
            case DBAdmin
                         dbAdmin -> new Admin(firstname, lastname, email, password);
            default -> throw new UnknownRoleException(dbBaseUser.getClass().getSimpleName());
        };
    }

    @Override
    default DBBaseUser fromDomainModel(BaseUser baseUser) {
        String firstname = baseUser.firstname.toString();
        String lastname = baseUser.lastname.toString();
        String email = baseUser.email.toString();
        String password = baseUser.password.toString();
        return switch (baseUser) {
            case SimpleUser simpleUser -> new DBSimpleUser(firstname, lastname, email, password);
            case Manager manager -> new DBManager(firstname, lastname, email, password, dbTeamMapper.fromDomainModel(manager.team));
            case Admin admin -> new DBAdmin(firstname, lastname, email, password);
            default -> throw new UnknownRoleException(baseUser.getClass().getSimpleName());
        };
    }
}
