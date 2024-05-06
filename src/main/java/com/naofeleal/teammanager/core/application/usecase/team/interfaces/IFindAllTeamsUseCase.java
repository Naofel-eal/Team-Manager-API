package com.naofeleal.teammanager.core.application.usecase.team.interfaces;

import com.naofeleal.teammanager.core.domain.model.team.Team;

import java.util.List;

public interface IFindAllTeamsUseCase {
    List<Team> execute();
}
