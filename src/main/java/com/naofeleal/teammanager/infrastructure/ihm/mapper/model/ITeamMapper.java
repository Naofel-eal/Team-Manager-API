package com.naofeleal.teammanager.infrastructure.ihm.mapper.model;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.ihm.model.team.response.TeamDTO;
import com.naofeleal.teammanager.shared.mapper.IBaseRoleMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {IUserMapper.class, IBaseRoleMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ITeamMapper {
    TeamDTO fromDomainModels(Team team);
    List<TeamDTO> fromDomainModels(List<Team> teams);
}
