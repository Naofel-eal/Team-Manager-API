package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDBAdminRepository extends JpaRepository<DBAdmin, Long> {
    Optional<DBAdmin> findByUserId(Long userId);
}
