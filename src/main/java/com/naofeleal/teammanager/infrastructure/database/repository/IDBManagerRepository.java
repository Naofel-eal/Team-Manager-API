package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDBManagerRepository extends JpaRepository<DBManager, Long> {
    Optional<DBManager> findByUserId(Long id);
}