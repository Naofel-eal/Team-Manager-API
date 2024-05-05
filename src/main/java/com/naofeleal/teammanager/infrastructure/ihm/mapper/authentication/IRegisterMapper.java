package com.naofeleal.teammanager.infrastructure.ihm.mapper.authentication;

import com.naofeleal.teammanager.core.application.usecase.authentication.dto.RegisterUserDTO;
import com.naofeleal.teammanager.infrastructure.ihm.model.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IRegisterMapper {
    RegisterUserDTO toDomain(RegisterRequest registerRequest);
}
