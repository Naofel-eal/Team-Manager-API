package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanAddMemberToATeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanRemoveMemberFromATeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.CreateTeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.interfaces.*;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.ITeamMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.FindAllTeamsResponse;
import com.naofeleal.teammanager.infrastructure.endpoint.model.team.response.TeamDTO;
import com.naofeleal.teammanager.infrastructure.security.service.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    private final IFindAllTeamsUseCase _findAllTeamsUseCase;
    private final IAddMemberUseCase _addMemberUseCase;
    private final IRemoveMemberUseCase _removeMemberUseCase;
    private final ICreateTeamUseCase _createTeamUseCase;
    private final IDeleteTeamUseCase _deleteTeamUseCase;
    private final ITeamMapper _teamMapper;
    private final UserDetailsServiceImpl _userDetailsService;

    public TeamController(
        IFindAllTeamsUseCase findAllTeamsUseCase,
        IAddMemberUseCase addMemberUseCase,
        IRemoveMemberUseCase removeMemberUseCase,
        ITeamMapper teamMapper,
        UserDetailsServiceImpl userDetailsService,
        ICreateTeamUseCase createTeamUseCase,
        IDeleteTeamUseCase deleteTeamUseCase
    ) {
        _findAllTeamsUseCase = findAllTeamsUseCase;
        _addMemberUseCase = addMemberUseCase;
        _removeMemberUseCase = removeMemberUseCase;
        _teamMapper = teamMapper;
        _userDetailsService = userDetailsService;
        _createTeamUseCase = createTeamUseCase;
        _deleteTeamUseCase = deleteTeamUseCase;
    }

    @GetMapping("/all")
    public ResponseEntity<FindAllTeamsResponse> findAllTeams() {
        List<Team> teams = _findAllTeamsUseCase.execute();
        List<TeamDTO> teamDTOs = _teamMapper.fromDomainModels(teams);
        FindAllTeamsResponse response = new FindAllTeamsResponse(teamDTOs);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{teamId}/members/{userEmail}")
    public ResponseEntity<TeamDTO> addMemberToTeam(
            @PathVariable Long teamId,
            @PathVariable String userEmail
    ) {
        BaseUser user = _userDetailsService.getLoggedUser();

        Team team = this._addMemberUseCase.execute(user, teamId, userEmail);
        TeamDTO teamDTO = this._teamMapper.fromDomainModel(team);
        return ResponseEntity.ok(teamDTO);
    }

    @DeleteMapping("/{teamId}/members/{userEmail}")
    public ResponseEntity<TeamDTO> removeMemberFromTeam(
            @PathVariable Long teamId,
            @PathVariable String userEmail
    ) {
        BaseUser initiator = _userDetailsService.getLoggedUser();
        Team team = this._removeMemberUseCase.execute(initiator, teamId, userEmail);
        TeamDTO teamDTO = this._teamMapper.fromDomainModel(team);
        return ResponseEntity.ok(teamDTO);
    }

    @PostMapping("/add/{futureManagerEmail}")
    public ResponseEntity<Void> createTeam(@PathVariable String futureManagerEmail) {
        BaseUser initiator = _userDetailsService.getLoggedUser();
        this._createTeamUseCase.execute(initiator, futureManagerEmail);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{managerEmail}")
    public ResponseEntity<Void> deleteTeam(@PathVariable String managerEmail) {
        BaseUser initiator = _userDetailsService.getLoggedUser();
        this._deleteTeamUseCase.execute(initiator, managerEmail);
        return ResponseEntity.ok().build();
    }
}
