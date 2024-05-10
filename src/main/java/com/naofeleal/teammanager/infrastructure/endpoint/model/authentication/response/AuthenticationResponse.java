package com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.response;

import com.naofeleal.teammanager.infrastructure.endpoint.model.user.UserDTO;

public record AuthenticationResponse(String token, UserDTO user) {}
