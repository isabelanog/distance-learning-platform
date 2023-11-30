package com.dlp.course.services;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.UserModel;
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

    boolean isUserSubscribedInCourse(UUID courseId, UUID userId);

    void saveUserSubscription(UUID courseId, UUID userId);
    void saveUserSubscriptionAndSendNotification(CourseModel course, UserModel user);
}
