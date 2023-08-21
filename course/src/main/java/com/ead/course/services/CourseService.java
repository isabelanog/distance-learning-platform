package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;


public interface CourseService {
    void deleteCourse(CourseModel courseModel);

    CourseModel saveCourse(CourseModel courseModel);

    Optional<CourseModel> getCourseById(UUID courseId);

    Page<CourseModel> getCourses(Specification<CourseModel> courseSpecification, Pageable pageable);
}
