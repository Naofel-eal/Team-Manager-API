package com.naofeleal.teammanager.core.application.usecase.authentication;

import com.naofeleal.teammanager.core.application.exception.user.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.core.domain.model.user.properties.Email;
import com.naofeleal.teammanager.core.domain.model.user.properties.Name;
import com.naofeleal.teammanager.core.domain.model.user.properties.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase implements IRegisterUseCase {
    private final IUserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;

    public RegisterUseCase(
        final IUserRepository userRepository,
        final PasswordEncoder passwordEncoder
    ) {
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(String firstname, String lastname, String email, String password) throws AlreadyUsedEmailException {
        if(this._userRepository.findByEmail(email).isPresent())
            throw new AlreadyUsedEmailException();

        SimpleUser user = new SimpleUser(
                new Name(firstname),
                new Name(lastname),
                new Email(email),
                Password.fromRaw(password, _passwordEncoder::encode)
        );

        this._userRepository.register(user);
    }
}