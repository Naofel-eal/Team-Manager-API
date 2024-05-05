package com.naofeleal.teammanager.core.application.usecase.authentication.interfaces;


public interface ILoginUseCase {
    String execute(String username, String password);
}
