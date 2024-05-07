package com.naofeleal.teammanager.infrastructure.endpoint.model.team.response;

import java.util.List;

public record FindAllTeamsResponse(List<TeamDTO> teams){}
