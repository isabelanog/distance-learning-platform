package com.ead.course.services;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;

import java.util.UUID;

public interface CourseUsersService {
    boolean isUserSubscribedToCourse(CourseModel course, UUID userId);

    void addUserToCourse(CoursesUsersModel coursesUsersModel);

}
