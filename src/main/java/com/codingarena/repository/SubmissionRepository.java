package com.codingarena.repository;

import com.codingarena.model.Submission;
import com.codingarena.model.User;
import com.codingarena.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUserOrderBySubmittedAtDesc(User user);
    List<Submission> findByProblemOrderBySubmittedAtDesc(Problem problem);
    Optional<Submission> findTopByUserAndProblemAndVerdict(User user, Problem problem, Submission.Verdict verdict);
    boolean existsByUserAndProblemAndVerdict(User user, Problem problem, Submission.Verdict verdict);
    long countByUserAndVerdict(User user, Submission.Verdict verdict);
}
