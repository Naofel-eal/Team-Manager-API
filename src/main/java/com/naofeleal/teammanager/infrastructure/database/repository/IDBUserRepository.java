package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDBUserRepository extends JpaRepository<DBUser, Long> {
    Optional<DBUser> findByEmail(String email);
}
