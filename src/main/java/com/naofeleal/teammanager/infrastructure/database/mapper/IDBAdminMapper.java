package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.user.Admin;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import com.naofeleal.teammanager.infrastructure.database.model.DBAdmin;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDBAdminMapper extends IGenericMapper<Admin, DBAdmin> {
    @Override
    default Admin toDomainModel(DBAdmin dbAdmin) {
        return new Admin(
                new Name(dbAdmin.user.firstname),
                new Name(dbAdmin.user.lastname),
                new Email(dbAdmin.user.email),
                new Password(dbAdmin.user.password)
        );
    }

    @Override
    default DBAdmin fromDomainModel(Admin admin) {
        DBUser dbUser = new DBUser(
                admin.id,
                admin.firstname.toString(),
                admin.lastname.toString(),
                admin.email.toString(),
                admin.password.toString(),
                null
        );
        return new DBAdmin(dbUser);
    }
}
