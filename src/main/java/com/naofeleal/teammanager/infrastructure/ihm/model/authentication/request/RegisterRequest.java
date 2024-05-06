package com.naofeleal.teammanager.infrastructure.ihm.model.authentication.request;

public record RegisterRequest(String firstname, String lastname, String email, String password) {}
