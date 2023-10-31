package com.dlp.course.services.impl;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.CoursesUsersModel;
import com.dlp.course.clients.AuthUserClient;
import com.dlp.course.repositories.CourseUsersRepository;
import com.dlp.course.services.CourseUsersService;
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

        authUserClient.sendUserSubscription(courseId, userId);

        return coursesUsersModel;
    }

    public boolean getCourseUserByUserId(UUID userId) {
        return courseUsersRepository.existsCoursesUsersModelByUserId(userId);
    }
    @Transactional
    @Override
    public void deleteCourseUserByUserId(UUID userId) {
        courseUsersRepository.deleteAllByUserId(userId);
    }
}
