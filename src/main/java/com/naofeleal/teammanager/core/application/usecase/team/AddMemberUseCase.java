package com.naofeleal.teammanager.core.application.usecase.team;

import com.naofeleal.teammanager.core.application.exception.team.TeamNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanAddMemberToATeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.interfaces.IAddMemberUseCase;
import com.naofeleal.teammanager.core.domain.exception.team.UserCanNotBeMemberException;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AddMemberUseCase implements IAddMemberUseCase {
    private final ITeamRepository _teamRepository;
    private final IUserRepository _userRepository;
    private final ICanAddMemberToATeamUseCase _canAddMemberToATeamUseCase;

    public AddMemberUseCase(
            final ITeamRepository teamRepository,
            final IUserRepository userRepository,
            final ICanAddMemberToATeamUseCase canAddMemberToATeamUseCase
    ) {
        _teamRepository = teamRepository;
        _userRepository = userRepository;
        _canAddMemberToATeamUseCase = canAddMemberToATeamUseCase;
    }

    @Override
    public Team execute(BaseUser user, String managerEmail, String userEmail)
            throws TeamNotFoundException, EmailNotFoundException, UserCanNotBeMemberException{

        Optional<BaseUser> optManager = _userRepository.findByEmail(managerEmail);
        if(optManager.isEmpty() || ((Manager) optManager.get()).team == null)
            throw new TeamNotFoundException(managerEmail);

        Manager manager = (Manager) optManager.get();
        Team team = manager.team;
        team.manager = manager;

        if (!_canAddMemberToATeamUseCase.execute(user, team))
            throw new UnauthorizedOperationException();

        Optional<BaseUser> optTargetUser = _userRepository.findByEmail(userEmail);
        if (optTargetUser.isEmpty())
            throw new EmailNotFoundException(userEmail);

        if (!Objects.equals(optTargetUser.get().getRole(), RoleCode.SIMPLE_USER.toString()))
            throw new UserCanNotBeMemberException();

        SimpleUser targetUser = (SimpleUser) optTargetUser.get();
        team.addMember(targetUser);
        return _teamRepository.save(team);
    }
}
