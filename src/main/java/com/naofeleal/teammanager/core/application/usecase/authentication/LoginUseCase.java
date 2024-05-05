package com.naofeleal.teammanager.core.application.usecase.authentication;

import com.naofeleal.teammanager.core.application.exception.authentication.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.repository.IJWTService;
import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.ILoginUseCase;
import com.naofeleal.teammanager.core.domain.model.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUseCase implements ILoginUseCase {
    private final AuthenticationManager _authenticationManager;
    private final IJWTService _jwtService;
    private final IUserRepository _userRepository;

    public LoginUseCase(
        final AuthenticationManager authenticationManager,
        final IJWTService jwtService,
        final IUserRepository userRepository
    ) {
        _authenticationManager = authenticationManager;
        _jwtService = jwtService;
        _userRepository = userRepository;
    }

    public String execute(String email, String password) {
        _authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                password
            )
        );
        Optional<User> user = _userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EmailNotFoundException(email);
        }
        return _jwtService.generateToken(user.get());
    }
}
