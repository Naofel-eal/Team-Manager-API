package com.naofeleal.teammanager.core.application.usecase.authentication;

import com.naofeleal.teammanager.core.application.exception.authentication.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.core.domain.model.user.Email;
import com.naofeleal.teammanager.core.domain.model.user.Name;
import com.naofeleal.teammanager.core.domain.model.user.Password;
import com.naofeleal.teammanager.core.domain.model.user.User;
import com.naofeleal.teammanager.core.domain.model.role.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCase implements IRegisterUseCase {
    private IUserRepository _userRepository;
    private final PasswordEncoder _passwordEncoder;

    public RegisterUseCase(
        final IUserRepository userRepository,
        final PasswordEncoder passwordEncoder
    ) {
        _userRepository = userRepository;
        _passwordEncoder = passwordEncoder;
    }

    @Override
    public void execute(RegisterUserDTO registerUserDTO) {
        User user = new User(
            new Name(registerUserDTO.firstname()),
            new Name(registerUserDTO.lastname()),
            new Email(registerUserDTO.email()),
            Password.fromRaw(registerUserDTO.password(), _passwordEncoder::encode),
            Role.USER
        );

        if(this._userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new AlreadyUsedEmailException();
        }
        this._userRepository.register(user);
    }
}