package com.ead.authuser.service;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;

import java.util.UUID;

public interface UserCoursesService {

    UserCoursesModel saveSubscription(UserCoursesModel userCoursesModel);

    boolean isUserSubscribedInCourse(UserModel userModel, UUID courseId);

    boolean hasUserSubscribedInCourse(UUID courseId);

    void deleteUserCourseByCourseId(UUID courseId);

}
