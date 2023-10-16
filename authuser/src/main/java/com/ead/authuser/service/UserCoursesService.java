package com.ead.authuser.service;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;

import java.util.UUID;

public interface UserCoursesService {

    UserCoursesModel addUserToCourse(UserCoursesModel userCoursesModel);

    boolean isUserSubscribedInCourse(UserModel userModel, UUID courseId);

    boolean hasRelationshipUserAndCourse(UUID courseId);

    void deleteUserCourseByCourseId(UUID courseId);

}
