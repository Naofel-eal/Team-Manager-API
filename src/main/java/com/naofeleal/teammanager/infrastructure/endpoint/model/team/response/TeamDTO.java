package com.naofeleal.teammanager.infrastructure.endpoint.model.team.response;

import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;

import java.util.List;

public record TeamDTO(Long id, UserDTO manager, List<UserDTO> members) {}
