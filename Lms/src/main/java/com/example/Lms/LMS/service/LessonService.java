package com.example.Lms.LMS.service;

import com.example.Lms.LMS.model.Lesson;
import com.example.Lms.LMS.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }


    public Lesson create(Lesson lesson) {

        return lessonRepository.save(lesson);
    }

    public Lesson update(Lesson lesson) {
        // Check if lesson exists
        if (lesson.getId() == null || !lessonRepository.existsById(lesson.getId())) {
            throw new RuntimeException("Lesson not found");
        }
        return lessonRepository.save(lesson);
    }

    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}
