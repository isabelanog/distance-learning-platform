package com.dlp.course.repositories;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.CoursesUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUsersRepository extends JpaRepository<CoursesUsersModel, UUID> {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);
    List<CoursesUsersModel> getAllByCourse_CourseId(@Param("courseId") UUID courseId);
    boolean existsCoursesUsersModelByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);
}
