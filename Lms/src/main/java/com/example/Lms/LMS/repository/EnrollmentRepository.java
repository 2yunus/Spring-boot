package com.example.Lms.LMS.repository;

import com.example.Lms.LMS.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
