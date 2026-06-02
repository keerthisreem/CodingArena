package com.codingarena.config;

import com.codingarena.model.Problem;
import com.codingarena.model.User;
import com.codingarena.repository.ProblemRepository;
import com.codingarena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin user
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@codingarena.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(User.Role.ADMIN)
                    .score(0)
                    .problemsSolved(0)
                    .build();
            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin / admin123");
        }

        // Seed sample problems
        if (problemRepository.count() == 0) {
            problemRepository.save(Problem.builder()
                    .title("Sum of Two Numbers")
                    .description("Given two integers A and B, print their sum.")
                    .inputFormat("Two space-separated integers A and B.")
                    .outputFormat("Print the sum of A and B.")
                    .constraints("1 ≤ A, B ≤ 10^9")
                    .sampleInput("3 5")
                    .sampleOutput("8")
                    .expectedOutput("8")
                    .difficulty(Problem.Difficulty.EASY)
                    .points(10)
                    .build());

            problemRepository.save(Problem.builder()
                    .title("Reverse a String")
                    .description("Given a string S, print the reverse of the string.")
                    .inputFormat("A single string S (no spaces).")
                    .outputFormat("Print the reversed string.")
                    .constraints("1 ≤ |S| ≤ 1000")
                    .sampleInput("hello")
                    .sampleOutput("olleh")
                    .expectedOutput("olleh")
                    .difficulty(Problem.Difficulty.EASY)
                    .points(10)
                    .build());

            problemRepository.save(Problem.builder()
                    .title("Check Prime Number")
                    .description("Given a number N, check if it is prime. Print YES if prime, NO otherwise.")
                    .inputFormat("A single integer N.")
                    .outputFormat("Print YES or NO.")
                    .constraints("1 ≤ N ≤ 10^6")
                    .sampleInput("7")
                    .sampleOutput("YES")
                    .expectedOutput("YES")
                    .difficulty(Problem.Difficulty.MEDIUM)
                    .points(20)
                    .build());

            problemRepository.save(Problem.builder()
                    .title("Fibonacci Sequence")
                    .description("Given N, print the first N numbers of the Fibonacci sequence separated by spaces.")
                    .inputFormat("A single integer N.")
                    .outputFormat("N Fibonacci numbers separated by spaces.")
                    .constraints("1 ≤ N ≤ 30")
                    .sampleInput("5")
                    .sampleOutput("0 1 1 2 3")
                    .expectedOutput("0 1 1 2 3")
                    .difficulty(Problem.Difficulty.MEDIUM)
                    .points(20)
                    .build());

            problemRepository.save(Problem.builder()
                    .title("Longest Common Subsequence")
                    .description("Given two strings, find the length of their longest common subsequence.")
                    .inputFormat("Two strings on separate lines.")
                    .outputFormat("Print the length of LCS.")
                    .constraints("1 ≤ |S1|, |S2| ≤ 100")
                    .sampleInput("ABCBDAB\nBDCAB")
                    .sampleOutput("4")
                    .expectedOutput("4")
                    .difficulty(Problem.Difficulty.HARD)
                    .points(30)
                    .build());

            System.out.println("✅ 5 sample problems seeded.");
        }
    }
}
