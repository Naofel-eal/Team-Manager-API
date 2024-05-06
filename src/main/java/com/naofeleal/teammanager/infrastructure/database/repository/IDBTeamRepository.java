package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.account.DBTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDBTeamRepository extends JpaRepository<DBTeam, Long> {}
