package com.naofeleal.teammanager.infrastructure.ihm.mapper.model;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.ihm.model.team.response.TeamDTO;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = IUserMapper.class)
public interface ITeamMapper extends IGenericMapper<Team, TeamDTO> {}
