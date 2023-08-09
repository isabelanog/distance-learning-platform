package com.ead.course.services.impl;

import com.ead.course.repositories.CourseUsersRepository;
import com.ead.course.services.CourseUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseUsersServiceImpl implements CourseUsersService {

    @Autowired
    CourseUsersRepository courseUsersRepository;


}
