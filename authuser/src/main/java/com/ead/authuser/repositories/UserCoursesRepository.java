package com.ead.authuser.repositories;

import com.ead.authuser.models.UserCoursesModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCoursesRepository extends JpaRepository<UserCoursesModel, UUID> {

}
