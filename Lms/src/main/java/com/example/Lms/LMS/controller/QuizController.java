package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Quiz;
import com.example.Lms.LMS.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ModelAndView getAllQuizzes(Model model) {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return new ModelAndView("quizzes");
    }

    @GetMapping("/quiz-form")
    public String getQuizById(@PathVariable Long id, Model model) {
        model.addAttribute("quiz", new Quiz());
        return "quiz-form";
    }

    @PostMapping("/create")
    public String createQuiz(@ModelAttribute Quiz quiz) {
        quizService.create(quiz);
        return "redirect:/quizzes";
    }

    @PutMapping("/edit/{id}")
    public String updateQuiz(@PathVariable Long id, @ModelAttribute Quiz quiz) {
        quiz.setId(id); // Ensure the correct ID is set for update
        quizService.update(quiz);
        return "redirect:/quizzes";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.delete(id);
        return "redirect:/quizzes";
    }
}
