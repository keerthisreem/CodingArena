package com.codingarena.controller;

import com.codingarena.model.Problem;
import com.codingarena.service.ProblemService;
import com.codingarena.service.UserService;
import com.codingarena.service.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final ProblemService problemService;
    private final UserService userService;
    private final JudgeService judgeService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalProblems", problemService.count());
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalSubmissions", judgeService.getAll().size());
        model.addAttribute("problems", problemService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/problems")
    public String listProblems(Model model) {
        model.addAttribute("problems", problemService.findAll());
        return "admin/problems";
    }

    @GetMapping("/problems/new")
    public String newProblemForm(Model model) {
        model.addAttribute("problem", new Problem());
        model.addAttribute("difficulties", Problem.Difficulty.values());
        model.addAttribute("isEdit", false);
        return "admin/problem-form";
    }

    @PostMapping("/problems/new")
    public String createProblem(@ModelAttribute Problem problem, RedirectAttributes redirectAttributes) {
        try {
            problemService.save(problem);
            redirectAttributes.addFlashAttribute("success", "Problem created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/problems";
    }

    @GetMapping("/problems/{id}/edit")
    public String editProblemForm(@PathVariable Long id, Model model) {
        Problem problem = problemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        model.addAttribute("problem", problem);
        model.addAttribute("difficulties", Problem.Difficulty.values());
        model.addAttribute("isEdit", true);
        return "admin/problem-form";
    }

    @PostMapping("/problems/{id}/edit")
    public String updateProblem(@PathVariable Long id, @ModelAttribute Problem problem,
                                RedirectAttributes redirectAttributes) {
        try {
            problem.setId(id);
            problemService.save(problem);
            redirectAttributes.addFlashAttribute("success", "Problem updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/problems";
    }

    @PostMapping("/problems/{id}/delete")
    public String deleteProblem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            problemService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Problem deleted.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        return "redirect:/admin/problems";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }
}
