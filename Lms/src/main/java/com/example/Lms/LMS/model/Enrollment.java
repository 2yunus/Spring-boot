package com.example.Lms.LMS.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String studentName;


    private String enrolledCourse;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date enrollmentDate;


    public Enrollment() {

    }

// Getters and Setters

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setEnrolledCourse(String enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public Date getEnrollmentDate() {

        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {

        this.enrollmentDate = enrollmentDate;
    }

    public Long getId() {

        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    // Constructors
    public Enrollment(Long id, String studentName, String enrolledCourse, Date enrollmentDate) {
        this.id=id;
        this.studentName=studentName;
        this.studentName=enrolledCourse;
        this.enrollmentDate=enrollmentDate;
    }

}