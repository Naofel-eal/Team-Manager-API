package com.naofeleal.teammanager.core.application.usecase.user;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.application.usecase.user.interfaces.IFindFreeUsersUseCase;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FindFreeUsersUseCase implements IFindFreeUsersUseCase {
    private final IUserRepository _userRepository;

    public FindFreeUsersUseCase(IUserRepository userRepository) {
        _userRepository = userRepository;
    }

    @Override
    public Set<SimpleUser> findFreeUsers() {
       return this._userRepository.findFreeSimpleUsers();
    }
}
