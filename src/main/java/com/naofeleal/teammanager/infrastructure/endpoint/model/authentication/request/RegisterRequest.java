package com.naofeleal.teammanager.infrastructure.endpoint.model.authentication.request;

public record RegisterRequest(String firstname, String lastname, String email, String password) {}
