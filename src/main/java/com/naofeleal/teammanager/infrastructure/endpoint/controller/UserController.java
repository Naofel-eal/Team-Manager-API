package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.core.application.usecase.authentication.interfaces.IRegisterUseCase;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.ICreateUserUseCase;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IDeleteUserUseCase;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IFindFreeUsersUseCase;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.IUserMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.response.FindFreeUsersResponse;
import com.naofeleal.teammanager.infrastructure.security.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IFindFreeUsersUseCase _findFreeUsersUseCase;
    private final IUserMapper _userMapper;
    private final UserDetailsServiceImpl _userDetailsService;
    private final ICreateUserUseCase _createUserUseCase;
    private final IDeleteUserUseCase _deleteUserUseCase;

    public UserController(
            IFindFreeUsersUseCase findFreeUsersUseCase,
            IUserMapper userMapper,
            ICreateUserUseCase createUserUseCase,
            UserDetailsServiceImpl userDetailsService,
            IDeleteUserUseCase deleteUserUseCase
    ) {
        this._findFreeUsersUseCase = findFreeUsersUseCase;
        this._userMapper = userMapper;
        this._createUserUseCase = createUserUseCase;
        this._userDetailsService = userDetailsService;
        this._deleteUserUseCase = deleteUserUseCase;
    }

    @GetMapping("/free")
    public ResponseEntity<FindFreeUsersResponse> getFreeUsers() {
        Set<SimpleUser> freeUsers = _findFreeUsersUseCase.findFreeUsers();
        List<UserDTO> freeUserDTOs = _userMapper.toDTOs(freeUsers.stream().toList());
        return ResponseEntity.ok(new FindFreeUsersResponse(freeUserDTOs));
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody RegisterUserDTO userDTO) {
        BaseUser initiator = _userDetailsService.getLoggedUser();
        _createUserUseCase.execute(
                initiator,
                userDTO.firstname(),
                userDTO.lastname(),
                userDTO.email(),
                userDTO.password()
        );
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        BaseUser initiator = _userDetailsService.getLoggedUser();
        this._deleteUserUseCase.execute(initiator, email);
        return ResponseEntity.noContent().build();
    }
}
