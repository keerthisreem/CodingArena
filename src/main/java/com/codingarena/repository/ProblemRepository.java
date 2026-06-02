package com.codingarena.repository;

import com.codingarena.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findByDifficulty(Problem.Difficulty difficulty);
    List<Problem> findAllByOrderByCreatedAtDesc();
}
