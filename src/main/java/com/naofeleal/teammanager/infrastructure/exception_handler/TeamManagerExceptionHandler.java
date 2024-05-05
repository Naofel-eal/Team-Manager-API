package com.naofeleal.teammanager.infrastructure.exception_handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.naofeleal.teammanager.core.application.exception.authentication.AlreadyUsedEmailException;
import com.naofeleal.teammanager.core.application.exception.authentication.EmailNotFoundException;
import com.naofeleal.teammanager.core.domain.exception.TeamManagerRuntimeException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidEmailException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidNameException;
import com.naofeleal.teammanager.core.domain.exception.authentication.InvalidPasswordException;
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
    private Map<Class<? extends TeamManagerRuntimeException>, HttpStatus> httpResponseStatusCodeByException = new HashMap<>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TeamManagerExceptionHandler() {
        // Authentication
        httpResponseStatusCodeByException.put(AlreadyUsedEmailException.class, HttpStatus.CONFLICT);
        httpResponseStatusCodeByException.put(InvalidEmailException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(InvalidPasswordException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(InvalidNameException.class, HttpStatus.BAD_REQUEST);
        httpResponseStatusCodeByException.put(EmailNotFoundException.class, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {TeamManagerRuntimeException.class})
    public ResponseEntity<Object> handleException(TeamManagerRuntimeException ex, WebRequest request) throws JsonProcessingException {
        final String response = new ExceptionResponse(ex.getErrorCode(), ex.getMessage()).toString();
        final HttpStatus responseStatus = this.httpResponseStatusCodeByException.get(ex.getClass());
        return handleExceptionInternal(ex, response, new HttpHeaders(), responseStatus, request);
    }
}
