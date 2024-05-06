package com.naofeleal.teammanager.infrastructure.ihm.model.team.response;

import com.naofeleal.teammanager.infrastructure.ihm.model.user.UserDTO;

import java.util.List;

public record TeamDTO(UserDTO manager, List<UserDTO> members) {}
