package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Lesson;
import com.example.Lms.LMS.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public ModelAndView getAllLessons(Model model) {
        List<Lesson> lessons = lessonService.getAllLessons();
        model.addAttribute("lessons", lessons);
        return new ModelAndView("lessons");
    }

    @GetMapping("/lesson-form")
    public String getLessonById(Model model) {
        model.addAttribute("lesson", new Lesson());
        return "lesson-form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Lesson lesson) {
        lessonService.create(lesson);
        return "redirect:/lessons";
    }

    @PutMapping("/{id}")
    public String updateLesson(@PathVariable Long id, @ModelAttribute Lesson lesson) {
        lesson.setId(id); // Ensure the correct ID is set for update
        lessonService.update(lesson);
        return "redirect:/lessons";
    }

    @DeleteMapping("/{id}")
    public String deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return "redirect:/lessons";
    }
}
