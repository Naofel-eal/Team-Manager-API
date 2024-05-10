package com.naofeleal.teammanager.infrastructure.database.repository;

import com.naofeleal.teammanager.infrastructure.database.model.DBTeam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface IDBTeamRepository extends JpaRepository<DBTeam, Long> {
    Optional<DBTeam> findById(@NonNull Long id);

//    @Override
//    @Transactional
//    @Modifying
//    @Query(value = "delete from team where id = ?1", nativeQuery = true)
//    void deleteById(Long teamId);
}
