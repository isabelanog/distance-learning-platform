package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUsersRepository extends JpaRepository<CoursesUsersModel, UUID> {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);
}
