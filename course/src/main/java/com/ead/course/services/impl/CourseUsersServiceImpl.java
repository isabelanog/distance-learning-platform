package com.ead.course.services.impl;

import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import com.ead.course.repositories.CourseUsersRepository;
import com.ead.course.services.CourseUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUsersServiceImpl implements CourseUsersService {

    @Autowired
    CourseUsersRepository courseUsersRepository;

    @Override
    public boolean isUserSubscribedToCourse(CourseModel course, UUID userId) {
        return courseUsersRepository.existsByCourseIdAndUserId(course.getCourseId(), userId);
    }

    @Override
    public void addUserToCourse(CoursesUsersModel coursesUsersModel) {
        courseUsersRepository.save(coursesUsersModel);
    }
}
