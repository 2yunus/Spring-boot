package com.example.Lms.LMS.service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Lms.LMS.model.Course;
import com.example.Lms.LMS.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    public List<Course> getAllCourses() {
        try {
            return repo.findAll();
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public void save(Course course) {
        try {
            repo.save(course);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public Course getCourseById(Long id) {
        try {
            return repo.findById(id).orElse(null);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }

    public Course findByName(String name) {
        try {
            return repo.findByName(name);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }
    public void deleteCourseById(Long id) {
        try {
            repo.deleteById(id);
        } catch (DataAccessException e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
            throw e; // Re-throw the exception to the caller
        }
    }
}
