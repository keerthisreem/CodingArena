package com.codingarena.service;

import com.codingarena.model.User;
import com.codingarena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken: " + username);
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered: " + email);
        }
        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(User.Role.USER)
                .score(0)
                .problemsSolved(0)
                .build();
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getLeaderboard() {
        return userRepository.findAllOrderByScoreDesc();
    }

    @Transactional
    public void addScore(User user, int points) {
        user.setScore(user.getScore() + points);
        user.setProblemsSolved(user.getProblemsSolved() + 1);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
