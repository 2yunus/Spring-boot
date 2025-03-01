package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.LoggedIn;
import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.repository.LoggedInRepository;
import com.example.Lms.LMS.repository.LoginRepository;
import com.example.Lms.LMS.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private LoginRepository studentRepository;

    @Autowired
    private LoggedInRepository loggedInRepository;

    public Student login(String email, String password) {
        // Implement your login logic here
        Student student = studentRepository.findByEmailAndPassword(email, password);
        if (student != null) {
            // Add the student ID to the loggedin table
            loggedInRepository.save(new LoggedIn(student.getId()));
        }
        return student;
    }

    public void logout(Long userId) {
        // Remove the user ID from the loggedin table
        loggedInRepository.deleteById(userId);
    }
}
