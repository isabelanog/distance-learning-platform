package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCoursesRepository extends JpaRepository<UserCoursesModel, UUID> {
    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);
}
