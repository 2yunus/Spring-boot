package com.example.Lms.LMS.controller;

import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/students")
public class RegisterController {

    private final StudentRepository studentRepository;

    @Autowired
    public RegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping("/register")
    public String registerStudent(Student student) {
        // Here you can perform validation and other necessary checks before saving the student
        studentRepository.save(student);
        return "redirect:/"; // Redirect to login page after successful registration
    }
}
