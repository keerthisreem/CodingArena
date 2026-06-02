package com.codingarena.service;

import com.codingarena.model.Problem;
import com.codingarena.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public List<Problem> findAll() {
        return problemRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Problem> findByDifficulty(Problem.Difficulty difficulty) {
        return problemRepository.findByDifficulty(difficulty);
    }

    public Optional<Problem> findById(Long id) {
        return problemRepository.findById(id);
    }

    @Transactional
    public Problem save(Problem problem) {
        return problemRepository.save(problem);
    }

    @Transactional
    public void deleteById(Long id) {
        problemRepository.deleteById(id);
    }

    public long count() {
        return problemRepository.count();
    }
}
