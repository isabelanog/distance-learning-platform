package com.ead.authuser.service.impl;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCoursesRepository;
import com.ead.authuser.service.UserCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserCoursesServiceImpl implements UserCoursesService {

    @Autowired
    UserCoursesRepository userCoursesRepository;

    @Override
    public UserCoursesModel addUserToCourse(UserCoursesModel userCoursesModel) {
        return userCoursesRepository.save(userCoursesModel);
    }

    @Override
    public boolean isUserSubscribedInCourse(UserModel userModel, UUID courseId) {
        return userCoursesRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public boolean hasRelationshipUserAndCourse(UUID courseId) {
        return userCoursesRepository.existsByCourseId(courseId);
    }
    @Transactional
    @Override
    public void deleteUserCourseByCourseId(UUID courseId) {
        userCoursesRepository.deleteAllByCourseId(courseId);
    }
}
