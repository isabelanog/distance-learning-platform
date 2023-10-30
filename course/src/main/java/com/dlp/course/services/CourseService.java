package com.dlp.course.services;

import com.dlp.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;


public interface CourseService {
    void deleteCourse(CourseModel courseModel);

    CourseModel createCourse(CourseModel courseModel);

    Optional<CourseModel> getCourseById(UUID courseId);

    Page<CourseModel> getCourses(Specification<CourseModel> courseSpecification, Pageable pageable);
}
