package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Enrollment;
import com.example.Lms.LMS.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment create(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment update(Enrollment enrollment) {
        // Check if enrollment exists
        if (enrollment.getId() == null || !enrollmentRepository.existsById(enrollment.getId())) {
            throw new RuntimeException("Enrollment not found");
        }
        return enrollmentRepository.save(enrollment);
    }

    public void delete(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
