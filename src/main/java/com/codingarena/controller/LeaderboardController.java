package com.codingarena.controller;

import com.codingarena.model.User;
import com.codingarena.service.JudgeService;
import com.codingarena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@RequiredArgsConstructor
public class LeaderboardController {

    private final UserService userService;
    private final JudgeService judgeService;

    @GetMapping("/leaderboard")
    public String leaderboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<User> users = userService.getLeaderboard();
        model.addAttribute("users", users);

        if (userDetails != null) {
            AtomicInteger rank = new AtomicInteger(1);
            int myRank = 1;
            for (User u : users) {
                if (u.getUsername().equals(userDetails.getUsername())) {
                    myRank = rank.get();
                    break;
                }
                rank.incrementAndGet();
            }
            model.addAttribute("myRank", myRank);
        }
        return "leaderboard/index";
    }

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("user", user);
        model.addAttribute("submissions", judgeService.getByUser(user));
        return "profile";
    }
}
