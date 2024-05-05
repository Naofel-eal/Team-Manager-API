package com.naofeleal.teammanager.infrastructure.ihm.controller;

import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.ILoginUseCase;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.infrastructure.ihm.mapper.authentication.IRegisterMapper;
import com.naofeleal.teammanager.infrastructure.ihm.model.request.AuthenticationRequest;
import com.naofeleal.teammanager.infrastructure.ihm.model.request.RegisterRequest;
import com.naofeleal.teammanager.infrastructure.ihm.model.response.AuthenticationResponse;
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
    private final IRegisterMapper _registerMapper;

    public AuthController(IRegisterUseCase registerUseCase, ILoginUseCase loginUseCase, IRegisterMapper registerMapper) {
        this._registerUseCase = registerUseCase;
        this._loginUseCase = loginUseCase;
        this._registerMapper = registerMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
        @Valid @RequestBody RegisterRequest request
    ) {
        RegisterUserDTO registerUserDTO = _registerMapper.toDomain(request);
        _registerUseCase.execute(registerUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @Valid @RequestBody AuthenticationRequest authenticationRequest
    ) {
        String jwt = _loginUseCase.execute(authenticationRequest.email(), authenticationRequest.password());
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
