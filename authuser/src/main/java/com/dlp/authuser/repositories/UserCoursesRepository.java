package com.dlp.authuser.repositories;

import com.dlp.authuser.models.UserCoursesModel;
import com.dlp.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface UserCoursesRepository extends JpaRepository<UserCoursesModel, UUID> {
    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    List<UserCoursesModel> getAllByUser_UserId(UUID userId);

    boolean existsByCourseId(UUID courseId);

    void deleteAllByCourseId(UUID courseId);
}
