package com.naofeleal.teammanager.infrastructure.database.mapper;

import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.database.model.account.DBTeam;
import com.naofeleal.teammanager.shared.mapper.IGenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = IDBUserMapper.class)
public interface IDBTeamMapper extends IGenericMapper<Team, DBTeam> {
}
