package com.naofeleal.teammanager.infrastructure.exception_handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naofeleal.teammanager.core.application.exception.team.TeamNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.exception.user.EmailNotFoundException;
import com.naofeleal.teammanager.core.application.exception.user.InvalidTokenException;
import com.naofeleal.teammanager.core.application.exception.user.UnauthorizedOperationException;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidEmailException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidNameException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidPasswordException;
import com.naofeleal.teammanager.core.domain.exception.team.*;
import com.naofeleal.teammanager.infrastructure.database.exception.UnknownRoleException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TeamManagerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Map<Class<? extends TeamManagerRuntimeException>, HttpStatus> httpResponseStatusCodeByException = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TeamManagerExceptionHandler() {
        httpResponseStatusCodeByException.put(AlreadyUsedEmailException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(InvalidEmailException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(InvalidPasswordException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(InvalidNameException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(EmailNotFoundException.class, HttpStatus.NOT_FOUND);
        httpResponseStatusCodeByException.put(InvalidTokenException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(UnknownRoleException.class, HttpStatus.NOT_FOUND);
        httpResponseStatusCodeByException.put(UserAlreadyMemberException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(UserNotMemberException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(TeamNotFoundException.class, HttpStatus.NOT_FOUND);
        httpResponseStatusCodeByException.put(UserCanNotBeMemberException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(UnauthorizedOperationException.class, HttpStatus.UNAUTHORIZED);
        httpResponseStatusCodeByException.put(UserCanNotBecomeManagerException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(NotAManagerException.class, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {TeamManagerRuntimeException.class})
    public ResponseEntity<Object> handleException(TeamManagerRuntimeException ex, WebRequest request) throws JsonProcessingException {
        final String response = new ExceptionResponse(ex.getErrorCode(), ex.getMessage()).toString();
        final HttpStatus responseStatus = this.httpResponseStatusCodeByException.get(ex.getClass());
        return handleExceptionInternal(ex, response, new HttpHeaders(), responseStatus, request);
    }
}
