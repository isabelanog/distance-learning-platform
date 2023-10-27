package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;
import java.util.List;

public interface UserCoursesRepository extends JpaRepository<UserCoursesModel, UUID> {
    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    List<UserCoursesModel> getAllByUser_UserId(UUID userId);

    boolean existsByCourseId(UUID courseId);

    void deleteAllByCourseId(UUID courseId);
}
