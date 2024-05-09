package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBManagerMapper;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBTeamMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBTeamRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DBTeamRepositoryAdapter implements ITeamRepository {
    private final IDBTeamRepository _teamRepository;
    private final IDBTeamMapper _teamMapper;
    private final IDBManagerMapper _managerMapper;

    public DBTeamRepositoryAdapter(IDBTeamRepository teamRepository, IDBTeamMapper teamMapper, IDBManagerMapper managerMapper) {
        _teamRepository = teamRepository;
        _teamMapper = teamMapper;
        _managerMapper = managerMapper;
    }

    @Override
    public Optional<Team> findById(long id) {
        Optional<DBTeam> optDBTeam = _teamRepository.findById(id);
        return optDBTeam.map(_teamMapper::toDomainModel);
    }

    @Override
    public List<Team> findAllTeams() {
        final List<DBTeam> dbTeams = _teamRepository.findAll();
        final Manager manager = _managerMapper.toDomainModel(dbTeams.getFirst().manager);
        return _teamMapper.toDomainModels(dbTeams);
    }

    @Override
    public Team save(Team team) {
        DBTeam dbTeam = _teamMapper.fromDomainModel(team);
        dbTeam = _teamRepository.save(dbTeam);
        return _teamMapper.toDomainModel(dbTeam);
    }

    @Override
    public void delete(Long teamId) {
        this._teamRepository.deleteById(teamId);
    }
}
