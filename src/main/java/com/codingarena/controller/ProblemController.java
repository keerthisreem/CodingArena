package com.codingarena.controller;

import com.codingarena.model.*;
import com.codingarena.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final UserService userService;
    private final JudgeService judgeService;

    @GetMapping("/problems")
    public String listProblems(@RequestParam(required = false) String difficulty, Model model,
                               @AuthenticationPrincipal UserDetails userDetails) {
        List<Problem> problems;
        if (difficulty != null && !difficulty.isBlank()) {
            problems = problemService.findByDifficulty(Problem.Difficulty.valueOf(difficulty.toUpperCase()));
        } else {
            problems = problemService.findAll();
        }
        model.addAttribute("problems", problems);
        model.addAttribute("selectedDifficulty", difficulty);

        // Pass solved problem IDs for the current user
        if (userDetails != null) {
            userService.findByUsername(userDetails.getUsername()).ifPresent(user -> {
                List<Long> solvedIds = judgeService.getByUser(user).stream()
                        .filter(s -> s.getVerdict() == Submission.Verdict.ACCEPTED)
                        .map(s -> s.getProblem().getId())
                        .distinct()
                        .toList();
                model.addAttribute("solvedIds", solvedIds);
            });
        }
        return "problems/list";
    }

    @GetMapping("/problems/{id}")
    public String viewProblem(@PathVariable Long id, Model model,
                              @AuthenticationPrincipal UserDetails userDetails) {
        Problem problem = problemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        model.addAttribute("problem", problem);

        if (userDetails != null) {
            userService.findByUsername(userDetails.getUsername()).ifPresent(user -> {
                boolean solved = judgeService.getByUser(user).stream()
                        .anyMatch(s -> s.getProblem().getId().equals(id) && s.getVerdict() == Submission.Verdict.ACCEPTED);
                model.addAttribute("alreadySolved", solved);
            });
        }
        return "problems/detail";
    }

    @GetMapping("/problems/{id}/submit")
    public String submitPage(@PathVariable Long id, Model model) {
        Problem problem = problemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        model.addAttribute("problem", problem);
        model.addAttribute("languages", Submission.Language.values());
        return "problems/submit";
    }

    @PostMapping("/problems/{id}/submit")
    public String submitCode(@PathVariable Long id,
                             @RequestParam String code,
                             @RequestParam String language,
                             @RequestParam String userOutput,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Problem problem = problemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Submission.Language lang = Submission.Language.valueOf(language.toUpperCase());
        Submission submission = judgeService.judge(user, problem, code, lang, userOutput);

        redirectAttributes.addFlashAttribute("submission", submission);
        redirectAttributes.addFlashAttribute("problem", problem);
        return "redirect:/problems/" + id + "/result";
    }

    @GetMapping("/problems/{id}/result")
    public String resultPage(@PathVariable Long id, Model model) {
        if (!model.containsAttribute("submission")) {
            return "redirect:/problems/" + id;
        }
        problemService.findById(id).ifPresent(p -> model.addAttribute("problem", p));
        return "problems/result";
    }
}
