package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Enrollment;
import com.example.Lms.LMS.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/Enrollments")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ModelAndView getAllEnrollments(Model model) {
        List<Enrollment> enrollmentsList = enrollmentService.getAllEnrollments();
        model.addAttribute("enrollments", enrollmentsList);
        return new ModelAndView("Enrollments");
    }

    @GetMapping("/enrollment-form")
    public String addEnrollment(Model model) {
        model.addAttribute("enrollment", new Enrollment());
        return "enrollment-form";
    }

    @PostMapping("/create")
    public String createEnrollment(@ModelAttribute Enrollment enrollment) {
        enrollmentService.create(enrollment);
        return "redirect:/Enrollments";
    }

    @PutMapping("/{id}")
    public String updateEnrollment(@PathVariable Long id, @ModelAttribute Enrollment enrollment) {
        enrollment.setId(id); // Ensure the correct ID is set for update
        enrollmentService.update(enrollment);
        return "redirect:/Enrollments";
    }

    @DeleteMapping("/{id}")
    public String deleteEnrollment(@PathVariable Long id) {
        enrollmentService.delete(id);
        return "redirect:/Enrollments";
    }
}
