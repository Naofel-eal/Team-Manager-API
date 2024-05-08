package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {IDBUserMapper.class, IBaseRoleMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface IDBTeamMapper extends IGenericMapper<Team, DBTeam> {
    @Override
    @Mapping(target = "manager.team", ignore = true)
    Team toDomainModel(DBTeam dbTeam);
}
