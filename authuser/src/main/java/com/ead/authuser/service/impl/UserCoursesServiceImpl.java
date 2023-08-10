package com.ead.authuser.service.impl;

import com.ead.authuser.repositories.UserCoursesRepository;
import com.ead.authuser.service.UserCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCoursesServiceImpl implements UserCoursesService {

    @Autowired
    UserCoursesRepository userCoursesRepository;
}
