package com.example.Lms.LMS.repository;

import com.example.Lms.LMS.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByName(String name);
}


