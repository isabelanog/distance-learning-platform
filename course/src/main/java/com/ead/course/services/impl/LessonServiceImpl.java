package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    LessonRepository lessonRepository;

    @Override
    public LessonModel save(LessonModel lessonModule) {
        return lessonRepository.save(lessonModule);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    @Override
    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }
}
