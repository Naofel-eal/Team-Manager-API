package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.ILoginUseCase;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.IUserMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.request.AuthenticationRequest;
import com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.response.AuthenticationResponse;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IRegisterUseCase _registerUseCase;
    private final ILoginUseCase _loginUseCase;
    private final IUserRepository _userRepository;
    private final IUserMapper _userMapper;

    public AuthController(
            IRegisterUseCase registerUseCase,
            ILoginUseCase loginUseCase,
            IUserRepository userRepository,
            IUserMapper userMapper
    ) {
        this._registerUseCase = registerUseCase;
        this._loginUseCase = loginUseCase;
        this._userRepository = userRepository;
        this._userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @Valid @RequestBody RegisterUserDTO registerUserDTO
    ) {
        _registerUseCase.execute(
                registerUserDTO.firstname(),
                registerUserDTO.lastname(),
                registerUserDTO.email(),
                registerUserDTO.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @Valid @RequestBody AuthenticationRequest authenticationRequest
    ) {
        String jwt = _loginUseCase.execute(authenticationRequest.email(), authenticationRequest.password());
        BaseUser user = _userRepository.findByEmail(authenticationRequest.email()).get();
        UserDTO userDTO = _userMapper.toDTO(user);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDTO));
    }
}
