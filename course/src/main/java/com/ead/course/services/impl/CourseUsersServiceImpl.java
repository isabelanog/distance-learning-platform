package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import com.ead.course.repositories.CourseUsersRepository;
import com.ead.course.services.CourseUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CourseUsersServiceImpl implements CourseUsersService {

    @Autowired
    CourseUsersRepository courseUsersRepository;

    @Autowired
    AuthUserClient authUserClient;

    @Override
    public boolean isUserSubscribedToCourse(CourseModel course, UUID userId) {
        return courseUsersRepository.existsByCourseAndUserId(course, userId);
    }

    @Override
    public CoursesUsersModel addUserToCourse(CoursesUsersModel coursesUsersModel) {
        return courseUsersRepository.save(coursesUsersModel);
    }
    @Transactional
    @Override
    public CoursesUsersModel saveAndSendUserSubscriptionInCourseToAuthUserMicroservice(CoursesUsersModel coursesUsersModel) {

        coursesUsersModel = courseUsersRepository.save(coursesUsersModel);

        UUID userId = coursesUsersModel.getUserId();
        UUID courseId = coursesUsersModel.getCourse().getCourseId();

        authUserClient.postSubscriptionUserInCourse(courseId, userId);
        return coursesUsersModel;
    }
}
