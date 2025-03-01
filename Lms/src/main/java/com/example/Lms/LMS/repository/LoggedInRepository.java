package com.example.Lms.LMS.repository;

import com.example.Lms.LMS.model.LoggedIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedInRepository extends JpaRepository<LoggedIn, Long> {
}
