package com.codingarena.service;

import com.codingarena.model.*;
import com.codingarena.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JudgeService {

    private final SubmissionRepository submissionRepository;
    private final UserService userService;

    @Transactional
    public Submission judge(User user, Problem problem, String code, Submission.Language language, String userOutput) {

        String expected = problem.getExpectedOutput().trim();
        String actual = (userOutput == null ? "" : userOutput.trim());

        Submission.Verdict verdict;

        if (actual.isEmpty()) {
            verdict = Submission.Verdict.RUNTIME_ERROR;
        } else if (normalise(actual).equals(normalise(expected))) {
            verdict = Submission.Verdict.ACCEPTED;
        } else {
            verdict = Submission.Verdict.WRONG_ANSWER;
        }

        Submission submission = Submission.builder()
                .user(user)
                .problem(problem)
                .code(code)
                .language(language)
                .verdict(verdict)
                .output(actual)
                .build();

        submissionRepository.save(submission);

        // Award points only on first accepted
        if (verdict == Submission.Verdict.ACCEPTED) {
            boolean alreadySolved = submissionRepository.existsByUserAndProblemAndVerdict(
                    user, problem, Submission.Verdict.ACCEPTED);
            // The save above already created one, so check count > 1
            long acceptedCount = submissionRepository.countByUserAndVerdict(user, Submission.Verdict.ACCEPTED);
            long totalForProblem = submissionRepository
                    .findByProblemOrderBySubmittedAtDesc(problem)
                    .stream()
                    .filter(s -> s.getUser().getId().equals(user.getId()) && s.getVerdict() == Submission.Verdict.ACCEPTED)
                    .count();
            if (totalForProblem == 1) {
                userService.addScore(user, problem.getPoints());
            }
        }

        return submission;
    }

    private String normalise(String s) {
        // Trim each line and compare ignoring blank lines at start/end
        return s.lines()
                .map(String::trim)
                .filter(l -> !l.isEmpty())
                .reduce("", (a, b) -> a + "\n" + b)
                .trim();
    }

    public List<Submission> getByUser(User user) {
        return submissionRepository.findByUserOrderBySubmittedAtDesc(user);
    }

    public List<Submission> getAll() {
        return submissionRepository.findAll();
    }
}
