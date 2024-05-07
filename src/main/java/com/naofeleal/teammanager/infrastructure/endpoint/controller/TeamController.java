package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.usecase.team.interfaces.IFindAllTeamsUseCase;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.ITeamMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.FindAllTeamsResponse;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.TeamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final IFindAllTeamsUseCase _findAllTeamsUseCase;
    private final ITeamMapper _teamMapper;

    public TeamController(
        IFindAllTeamsUseCase findAllTeamsUseCase,
        ITeamMapper teamMapper
    ) {
        _findAllTeamsUseCase = findAllTeamsUseCase;
        _teamMapper = teamMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<FindAllTeamsResponse> findAllTeams() {
        List<Team> teams = _findAllTeamsUseCase.execute();
        List<TeamDTO> teamDTOs = _teamMapper.fromDomainModels(teams);
        FindAllTeamsResponse response = new FindAllTeamsResponse(teamDTOs);
        return ResponseEntity.ok(response);
    }
}
