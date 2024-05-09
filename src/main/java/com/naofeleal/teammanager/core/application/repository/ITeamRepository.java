package com.naofeleal.teammanager.core.application.repository;

import com.naofeleal.teammanager.core.domain.model.team.Team;

import java.util.List;
import java.util.Optional;

public interface ITeamRepository {
    Optional<Team> findById(long id);
    List<Team> findAllTeams();
    Team save(Team team);
    void delete(Long teamId);
}
