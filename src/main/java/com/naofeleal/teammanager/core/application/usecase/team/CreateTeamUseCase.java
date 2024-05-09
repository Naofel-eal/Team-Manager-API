package com.naofeleal.teammanager.core.application.usecase.team;

import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanCreateTeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.interfaces.ICreateTeamUseCase;
import com.naofeleal.teammanager.core.domain.exception.team.UserCanNotBecomeManagerException;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.team.Team;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import org.springframework.stereotype.Service;


import java.util.Objects;
import java.util.Optional;

@Service
public class CreateTeamUseCase implements ICreateTeamUseCase {
    private final IUserRepository _userRepository;
    private final ICanCreateTeamUseCase _canCreateTeamUseCase;

    public CreateTeamUseCase(
            IUserRepository userRepository,
            ICanCreateTeamUseCase canCreateTeamUseCase
    ) {
        this._userRepository = userRepository;
        this._canCreateTeamUseCase = canCreateTeamUseCase;
    }

    @Override
    public void execute(BaseUser initiator, String futureManagerEmail)
            throws UnauthorizedOperationException, UserCanNotBecomeManagerException, EmailNotFoundException {
        if (!_canCreateTeamUseCase.execute(initiator))
            throw new UnauthorizedOperationException();

        Optional<BaseUser> optFutureManager = this._userRepository.findByEmail(futureManagerEmail);
        if (optFutureManager.isEmpty())
            throw new EmailNotFoundException(futureManagerEmail);

        if (!Objects.equals(optFutureManager.get().getRole(), RoleCode.SIMPLE_USER.toString()))
            throw new UserCanNotBecomeManagerException();

        Manager manager = ((SimpleUser) optFutureManager.get()).upgradeToManager();
        manager.team = new Team(manager);
        this._userRepository.save(manager);
    }
}
