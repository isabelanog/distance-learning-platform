package com.ead.course.repositories;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUsersRepository extends JpaRepository<CoursesUsersModel, UUID> {
    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);
    List<CoursesUsersModel> getAllByCourse_CourseId(@Param("courseId") UUID courseId);
    boolean existsCoursesUsersModelByUserId(UUID userId);
    void deleteAllByUserId(UUID userId);
}
