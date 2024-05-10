package com.naofeleal.teammanager.infrastructure.database.adapter;

import com.naofeleal.teammanager.core.application.repository.IUserRepository;
import com.naofeleal.teammanager.core.domain.model.role.RoleCode;
import com.naofeleal.teammanager.core.domain.model.user.Admin;
import com.naofeleal.teammanager.core.domain.model.user.BaseUser;
import com.naofeleal.teammanager.core.domain.model.user.Manager;
import com.naofeleal.teammanager.core.domain.model.user.SimpleUser;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBAdminMapper;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBManagerMapper;
import com.naofeleal.teammanager.infrastructure.database.mapper.IDBUserMapper;
import com.naofeleal.teammanager.infrastructure.database.model.DBAdmin;
import com.naofeleal.teammanager.infrastructure.database.model.DBManager;
import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBAdminRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBManagerRepository;
import com.naofeleal.teammanager.infrastructure.database.repository.IDBUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class DBUserRepositoryAdapter implements IUserRepository {
    private final IDBUserRepository _userRepository;
    private final IDBManagerRepository _managerRepository;
    private final IDBAdminRepository _adminRepository;
    private final IDBManagerMapper _managerMapper;
    private final IDBUserMapper _userMapper;
    private final IDBAdminMapper _adminMapper;

    public DBUserRepositoryAdapter(
            final IDBUserRepository userRepository,
            final IDBUserMapper userMapper,
            final IDBManagerRepository managerRepository,
            final IDBManagerMapper managerMapper,
            final IDBAdminRepository adminRepository,
            final IDBAdminMapper adminMapper
    ) {
        _userRepository = userRepository;
        _userMapper = userMapper;
        _managerRepository = managerRepository;
        _managerMapper = managerMapper;
        _adminRepository = adminRepository;
        _adminMapper = adminMapper;
    }

    @Override
    public void delete(BaseUser user) {
        _userRepository.deleteById(user.id);
    }

    @Override
    public void save(BaseUser user) {
        if (user instanceof SimpleUser)
            _userRepository.save(_userMapper.fromDomainModel((SimpleUser) user));
        else if (user instanceof Manager) {
            DBManager dbManager = _managerMapper.fromDomainModel((Manager) user);
            _managerRepository.save(dbManager);
        }
        else if (user instanceof Admin)
            _adminRepository.save(_adminMapper.fromDomainModel((Admin) user));
    }

    @Override
    public void register(BaseUser user) {
        DBUser dbUser = _userMapper.fromDomainModel((SimpleUser) user);
        _userRepository.save(dbUser);
    }

    @Override
    public Optional<BaseUser> findByEmail(String email) {
        Optional<DBUser> user = _userRepository.findByEmail(email);
        if (user.isPresent()) {
            Optional<DBManager> manager = _managerRepository.findByUserId(user.get().id);
            if (manager.isPresent())
                return manager.map(_managerMapper::toDomainModel);

            Optional<DBAdmin> admin = _adminRepository.findByUserId(user.get().id);
            if (admin.isPresent())
                return admin.map(_adminMapper::toDomainModel);
        }
        return user.map(_userMapper::toDomainModel);
    }

    @Override
    public Set<SimpleUser> findFreeSimpleUsers() {
        Set<DBUser> dbUsers = _userRepository.findByTeamIsNullAndAdminIsNullAndManagerIsNull();
        return dbUsers.stream()
            .map(_userMapper::toDomainModel)
            .collect(Collectors.toSet());
    }
}