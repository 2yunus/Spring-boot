package com.example.Lms.LMS.model;
import jakarta.persistence.*;


@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String questions;

    public Quiz() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public Quiz(Long id, String title, String questions, Course course, int weight) {
        this.id = id;
        this.title = title;
        this.questions = questions;

        this.weight = weight;
    }

    private int weight;

}