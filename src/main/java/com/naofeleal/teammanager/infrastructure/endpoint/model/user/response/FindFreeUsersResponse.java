package com.naofeleal.teammanager.infrastructure.endpoint.model.user.response;

import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;

import java.util.List;

public record FindFreeUsersResponse(List<UserDTO> freeUsers) {}
