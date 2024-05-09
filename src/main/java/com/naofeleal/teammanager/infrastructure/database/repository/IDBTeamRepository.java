package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface IDBTeamRepository extends JpaRepository<DBTeam, Long> {
    Optional<DBTeam> findById(@NonNull Long id);
}
