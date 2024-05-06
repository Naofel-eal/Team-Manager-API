package com.naofeleal.teammanager.core.application.repository;

import com.naofeleal.teammanager.core.domain.model.team.Team;

import java.util.List;

public interface ITeamRepository {
    List<Team> findAllTeams();
}
