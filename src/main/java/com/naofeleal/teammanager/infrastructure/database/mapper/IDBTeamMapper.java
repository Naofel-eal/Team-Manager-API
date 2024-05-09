package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {IDBManagerMapper.class, IDBUserMapper.class}
)
public interface IDBTeamMapper {
    IDBManagerMapper managerMapper = Mappers.getMapper(IDBManagerMapper.class);
    IDBUserMapper userMapper = Mappers.getMapper(IDBUserMapper.class);

    default Team toDomainModel(DBTeam dbTeam) {
        return new Team(
                dbTeam.id,
                managerMapper.toDomainModel(dbTeam.manager),
                userMapper.toDomainModels(dbTeam.members)
        );
    }

    default DBTeam fromDomainModel(Team team) {
        return new DBTeam(
                team.id,
                managerMapper.fromDomainModel(team.manager),
                userMapper.fromDomainModels(team.members.stream().toList())
        );
    }

    List<Team> toDomainModels(Collection<DBTeam> dbTeams);
}
