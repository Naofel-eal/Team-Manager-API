package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDBTeamRepository extends JpaRepository<DBTeam, Long> {}
