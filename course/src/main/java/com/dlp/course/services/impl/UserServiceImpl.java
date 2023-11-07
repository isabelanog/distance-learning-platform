package com.dlp.course.services.impl;

import com.dlp.course.models.UserModel;
import com.dlp.course.repositories.UserRepository;
import com.dlp.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<UserModel> getUsers(Specification<UserModel> userSpecification, Pageable pageable) {
        return userRepository.findAll(userSpecification, pageable);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void deleteUserById(UUID userId) {
        userRepository.deleteById(userId);
    }
}
