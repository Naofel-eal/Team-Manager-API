package com.naofeleal.teammanager.core.application.usecase.team;

import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.ITeamRepository;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanDeleteTeamUseCase;
import com.naofeleal.teammanager.core.application.usecase.team.interfaces.IDeleteTeamUseCase;
import com.naofeleal.teammanager.core.domain.exception.team.NotAManagerException;
import com.naofeleal.teammanager.core.domain.exception.team.UserCanNotBecomeManagerException;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DeleteTeamUseCase implements IDeleteTeamUseCase {
    private final IUserRepository _userRepository;
    private final ITeamRepository _teamRepository;
    private final ICanDeleteTeamUseCase _canDeleteTeamUseCase;

    public DeleteTeamUseCase(
            IUserRepository userRepository,
            ICanDeleteTeamUseCase canDeleteTeamUseCase,
            ITeamRepository teamRepository
    ) {
        this._userRepository = userRepository;
        this._canDeleteTeamUseCase = canDeleteTeamUseCase;
        this._teamRepository = teamRepository;
    }

    @Override
    public void execute(BaseUser initiator, String managerEmail)
            throws UnauthorizedOperationException, UserCanNotBecomeManagerException, EmailNotFoundException {
        if (!_canDeleteTeamUseCase.execute(initiator))
            throw new UnauthorizedOperationException();

        Optional<BaseUser> optManager = this._userRepository.findByEmail(managerEmail);
        if (optManager.isEmpty())
            throw new EmailNotFoundException(managerEmail);

        if (!Objects.equals(optManager.get().getRole(), RoleCode.MANAGER.toString()))
            throw new NotAManagerException(managerEmail);

        Manager manager = (Manager) optManager.get();
        this._teamRepository.delete(manager.team.id);
    }
}
