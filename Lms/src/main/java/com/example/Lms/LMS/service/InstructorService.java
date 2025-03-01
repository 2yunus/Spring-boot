package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Instructor;
import com.example.Lms.LMS.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repo;

    public List<Instructor> getAllInstructors() {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public void save(Instructor instructor) {
        try {
            repo.save(instructor);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public Instructor getInstructorById(Long id) {
        try {
            return repo.findById(id).orElse(null);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public void deleteInstructorById(Long id) {
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }
}
