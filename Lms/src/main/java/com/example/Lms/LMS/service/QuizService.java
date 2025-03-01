package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Quiz;
import com.example.Lms.LMS.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz create(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz update(Quiz quiz) {
        // Check if quiz exists
        if (quiz.getId() == null || !quizRepository.existsById(quiz.getId())) {
            throw new RuntimeException("Quiz not found");
        }
        return quizRepository.save(quiz);
    }

    public void delete(Long id) {
        quizRepository.deleteById(id);
    }
}
