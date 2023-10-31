package com.dlp.authuser.service.impl;

import com.dlp.authuser.clients.CourseClient;
import com.dlp.authuser.models.UserCoursesModel;
import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.repositories.UserCoursesRepository;
import com.dlp.authuser.service.UserCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserCoursesServiceImpl implements UserCoursesService {

    @Autowired
    UserCoursesRepository userCoursesRepository;
    @Autowired
    CourseClient courseClient;

    @Override
    public UserCoursesModel saveSubscription(UserCoursesModel userCoursesModel) {
        return userCoursesRepository.save(userCoursesModel);
    }

    @Override
    public boolean isUserSubscribedInCourse(UserModel userModel, UUID courseId) {
        return userCoursesRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public boolean hasUserSubscribedInCourse(UUID courseId) {
        return userCoursesRepository.existsByCourseId(courseId);
    }
    @Transactional
    @Override
    public void deleteUserCourseByCourseId(UUID courseId) {
        userCoursesRepository.deleteAllByCourseId(courseId);
    }


}
