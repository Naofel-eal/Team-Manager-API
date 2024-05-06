package com.naofeleal.teammanager.core.application.usecase.team;

import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.application.usecase.team.interfaces.IFindAllTeamsUseCase;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindAllTeamsUseCase implements IFindAllTeamsUseCase {
    private final ITeamRepository _teamRepository;

    public FindAllTeamsUseCase(ITeamRepository teamRepository) {
        _teamRepository = teamRepository;
    }

    public List<Team> execute() {
        return _teamRepository.findAllTeams();
    }
}
