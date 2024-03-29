package com.dlp.course.services;

import com.dlp.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(LessonModel lessonModule);

    Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId);

    void delete(LessonModel lessonModel);

    List<LessonModel> findAllLessonsIntoModule(UUID moduleId);

    Page<LessonModel> findAllLessonsIntoModule(Specification<LessonModel> lessonSpecification, Pageable pageable);

}
