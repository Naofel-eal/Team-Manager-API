package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IDBUserRepository extends JpaRepository<DBUser, Long> {
    Optional<DBUser> findByEmail(String email);

    @Query("SELECT u FROM DBUser u WHERE u.team IS NULL AND u.role = 'SIMPLE_USER'")
    Set<DBUser> findUsersWithoutTeamAndWithSimpleUserRole();

}
