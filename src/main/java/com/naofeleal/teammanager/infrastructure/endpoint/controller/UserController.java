package com.naofeleal.teammanager.infrastructure.endpoint.controller;

import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IFindFreeUsersUseCase;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.infrastructure.endpoint.mapper.model.IUserMapper;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;
import com.naofeleal.teammanager.infrastructure.endpoint.model.user.response.FindFreeUsersResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IFindFreeUsersUseCase _findFreeUsersUseCase;
    private final IUserMapper _userMapper;

    public UserController(IFindFreeUsersUseCase findFreeUsersUseCase, IUserMapper userMapper) {
        this._findFreeUsersUseCase = findFreeUsersUseCase;
        this._userMapper = userMapper;
    }

    @GetMapping("/free")
    public ResponseEntity<FindFreeUsersResponse> getFreeUsers() {
        Set<SimpleUser> freeUsers = _findFreeUsersUseCase.findFreeUsers();
        List<UserDTO> freeUserDTOs = _userMapper.toDTOs((List<? extends BaseUser>) freeUsers.stream().toList());
        return ResponseEntity.ok(new FindFreeUsersResponse(freeUserDTOs));
    }
}
