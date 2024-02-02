package com.dlp.course.repositories;

import com.dlp.course.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END AS BIT FROM courses_users WHERE courses_users.course_id= :courseId AND courses_users.user_id= :userId",
            nativeQuery = true)
    int existsByCourseAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);
    @Modifying
    @Query(value = "INSERT INTO courses_users (course_id, user_id) VALUES ( :courseId, :userId)", nativeQuery = true)
    void subscribeUser(@Param("courseId") String courseId, @Param("userId") String userId);

    @Modifying
    @Query(value = "DELETE FROM courses_users where course_id = :courseId", nativeQuery = true)
    void deleteCourseUserByCourseId(@Param("courseId") String courseId);

    @Modifying
    @Query(value = "DELETE FROM courses_users where user_id = :userId", nativeQuery = true)
    void deleteCourseUserByUserId(@Param("userId") String userId);

}
