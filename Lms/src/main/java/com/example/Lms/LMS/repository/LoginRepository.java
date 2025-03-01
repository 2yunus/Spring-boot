package com.example.Lms.LMS.repository;

import com.example.Lms.LMS.model.LoggedIn;
import com.example.Lms.LMS.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Student, Long>{
    Student findByEmailAndPassword(String email, String password);

}