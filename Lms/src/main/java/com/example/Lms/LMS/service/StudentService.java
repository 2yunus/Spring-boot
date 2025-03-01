package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Student;
import com.example.Lms.LMS.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
;


@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }




    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public Student save(Student student) {
        try {
            return studentRepository.save(student);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public Student getStudentById(Long id) {
        try {
            return studentRepository.findById(id).orElse(null);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public void deleteStudentById(Long id) {
        try {
            studentRepository.deleteById(id);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }
}
