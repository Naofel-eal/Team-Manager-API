package com.naofeleal.teammanager.core.application.usecase.user;

import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanDeleteUserUseCase;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IDeleteUserUseCase;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteUserUseCase implements IDeleteUserUseCase {
    private final ICanDeleteUserUseCase _canDeleteUserUseCase;
    private final IUserRepository _userRepository;

    public DeleteUserUseCase(
            final ICanDeleteUserUseCase canDeleteUserUseCase,
            final IUserRepository userRepository
    ) {
        _canDeleteUserUseCase = canDeleteUserUseCase;
        _userRepository = userRepository;
    }

    @Override
    public void execute(BaseUser initiator, String email)
        throws EmailNotFoundException, UnauthorizedOperationException {
        Optional<BaseUser> optUser = _userRepository.findByEmail(email);
        if(optUser.isEmpty())
            throw new EmailNotFoundException(email);

        BaseUser userToDelete = optUser.get();

        if (!_canDeleteUserUseCase.execute(initiator, userToDelete))
            throw new UnauthorizedOperationException();

        this._userRepository.delete(userToDelete);
    }
}
