package com.codingarena.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Verdict verdict;

    @Column(columnDefinition = "TEXT")
    private String output;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @PrePersist
    protected void onSubmit() {
        if (submittedAt == null) submittedAt = LocalDateTime.now();
    }

    public enum Language {
        JAVA, PYTHON, CPP
    }

    public enum Verdict {
        ACCEPTED, WRONG_ANSWER, RUNTIME_ERROR, COMPILE_ERROR, PENDING
    }
}
