package com.example.Lms.LMS.model;

import jakarta.persistence.*;

@Entity
@Table(name = "instructor")
public class Instructor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String courseTeaching;

    public Instructor(Long id, String name, String email, String courseTeaching) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courseTeaching = courseTeaching;
    }

    public Instructor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseTeaching() {
        return courseTeaching;
    }

    public void setCourseTeaching(String courseTeaching) {
        this.courseTeaching = courseTeaching;
    }
}
