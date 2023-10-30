package com.dlp.course.services.impl;

import com.dlp.course.repositories.UserRepository;
import com.dlp.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
}
