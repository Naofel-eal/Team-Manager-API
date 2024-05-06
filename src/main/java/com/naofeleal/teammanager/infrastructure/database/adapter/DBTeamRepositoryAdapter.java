package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBTeamMapper;
import com.naofeleal.teammanager.infrastructure.database.model.account.DBTeam;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBTeamRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DBTeamRepositoryAdapter implements ITeamRepository {
    private final IDBTeamRepository _teamRepository;
    private final IDBTeamMapper _teamMapper;

    public DBTeamRepositoryAdapter(IDBTeamRepository teamRepository, IDBTeamMapper teamMapper) {
        _teamRepository = teamRepository;
        _teamMapper = teamMapper;
    }

    @Override
    public List<Team> findAllTeams() {
        final List<DBTeam> dbTeams = _teamRepository.findAll();
        return _teamMapper.toDomainModels(dbTeams);
    }
}
