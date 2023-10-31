package com.dlp.course.services;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.CoursesUsersModel;

import java.util.UUID;

public interface CourseUsersService {
    boolean isUserSubscribedToCourse(CourseModel course, UUID userId);

    CoursesUsersModel addUserToCourse(CoursesUsersModel coursesUsersModel);

    CoursesUsersModel saveAndSendUserSubscriptionInCourseToAuthUserMicroservice(CoursesUsersModel coursesUsersModel);
    boolean getCourseUserByUserId(UUID userId);

    void deleteCourseUserByUserId(UUID userId);

}
