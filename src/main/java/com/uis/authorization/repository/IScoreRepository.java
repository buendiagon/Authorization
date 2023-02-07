package com.uis.authorization.repository;

import com.uis.authorization.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @autor Juan David Morantes Vergara
 */
@Repository
public interface IScoreRepository extends JpaRepository<Score,Long> {
    @Query("FROM Score s WHERE s.id_input=:i")
    List<Score> getScoreByIdInput(Long i);

    @Query("FROM Score s WHERE s.id_user=:i")
    List<Score> getScoreByIdUser(Long i);
}
