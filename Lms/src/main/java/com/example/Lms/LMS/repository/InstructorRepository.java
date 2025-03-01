package com.example.Lms.LMS.repository;

import com.example.Lms.LMS.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
}
