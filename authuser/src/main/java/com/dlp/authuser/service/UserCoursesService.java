package com.dlp.authuser.service;

import com.dlp.authuser.models.UserCoursesModel;
import com.dlp.authuser.models.UserModel;

import java.util.UUID;

public interface UserCoursesService {

    UserCoursesModel saveSubscription(UserCoursesModel userCoursesModel);

    boolean isUserSubscribedInCourse(UserModel userModel, UUID courseId);

    boolean hasUserSubscribedInCourse(UUID courseId);

    void deleteUserCourseByCourseId(UUID courseId);

}
