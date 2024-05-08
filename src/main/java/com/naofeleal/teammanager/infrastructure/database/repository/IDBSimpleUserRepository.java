package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBBaseUser;
import com.naofeleal.teammanager.infrastructure.database.model.DBSimpleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface IDBSimpleUserRepository extends JpaRepository<DBSimpleUser, Long> {
    Optional<DBSimpleUser> findByEmail(String email);

    @Query("SELECT u FROM DBSimpleUser u WHERE u.team IS NULL")
    Set<DBBaseUser> findSimpleUsersWithoutTeam();
}
