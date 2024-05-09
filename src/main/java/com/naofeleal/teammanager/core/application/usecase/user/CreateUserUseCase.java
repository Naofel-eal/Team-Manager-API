package com.naofeleal.teammanager.core.application.usecase.user;

import com.naofeleal.teammanager.core.application.exception.user.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.permission.interfaces.ICanCreateUserUseCase;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.ICreateUserUseCase;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase implements ICreateUserUseCase {
    private final ICanCreateUserUseCase _canCreateUserUseCase;
    private final IUserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;

    public CreateUserUseCase(
            ICanCreateUserUseCase canCreateUserUseCase,
            IUserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        _canCreateUserUseCase = canCreateUserUseCase;
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(BaseUser initiator, String firstname, String lastname, String email, String password)
            throws UnauthorizedOperationException, AlreadyUsedEmailException {
        if (!_canCreateUserUseCase.execute(initiator))
            throw new UnauthorizedOperationException();

        if(this._userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyUsedEmailException();
        }

        SimpleUser user = new SimpleUser(
                new Name(firstname),
                new Name(lastname),
                new Email(email),
                Password.fromRaw(password, _passwordEncoder::encode)
        );

        this._userRepository.register(user);
    }
}
