package com.codingarena.controller;

import com.codingarena.service.ProblemService;
import com.codingarena.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProblemService problemService;
    private final UserService userService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalProblems", problemService.count());
        model.addAttribute("totalUsers", userService.findAll().size());
        return "home";
    }
}
