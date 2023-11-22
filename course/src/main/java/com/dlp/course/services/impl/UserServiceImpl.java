package com.dlp.course.services.impl;

import com.dlp.course.models.UserModel;
import com.dlp.course.repositories.CourseRepository;
import com.dlp.course.repositories.UserRepository;
import com.dlp.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Override
    public Page<UserModel> getUsers(Specification<UserModel> userSpecification, Pageable pageable) {
        return userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Transactional
    @Override
    public void deleteUserById(UUID userId) {
        courseRepository.deleteCourseUserByUserId(userId.toString());
        userRepository.deleteById(userId);
    }

    @Override
    public Optional<UserModel> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }
}
