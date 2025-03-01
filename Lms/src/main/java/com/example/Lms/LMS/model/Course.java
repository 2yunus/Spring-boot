package com.example.Lms.LMS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Course(Long id, String name, String description, String coursecode) {
        this.id = id;
        this.name = name;
        this.description = description;
        Coursecode = coursecode;
    }

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private  String Coursecode;

    public Course() {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoursecode() {
        return Coursecode;
    }

    public void setCoursecode(String coursecode) {
        Coursecode = coursecode;
    }
}
