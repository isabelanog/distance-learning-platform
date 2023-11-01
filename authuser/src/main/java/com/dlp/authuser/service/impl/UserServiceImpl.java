package com.dlp.authuser.service.impl;

import com.dlp.authuser.clients.CourseClient;
import com.dlp.authuser.dtos.UserEventDto;
import com.dlp.authuser.enums.ActionType;
import com.dlp.authuser.publishers.UserEventPublisher;
import com.dlp.authuser.repositories.UserRepository;
import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserEventPublisher userEventPublisher;

    @Override
    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserModel> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void delete(UserModel user) {
        userRepository.delete(user);
    }

    @Override
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> getUsers(Specification<UserModel> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }

    @Transactional
    @Override
    public void deleteUser(UserModel user) {
        userRepository.delete(user);
    }
    @Transactional
    @Override
    public UserModel saveUserAndPublishEvent(UserModel user) {

        user = save(user); //save in database
        UserEventDto userEventDto = user.convertToUserEventDto();
        userEventPublisher.publishUserEvent(userEventDto, ActionType.CREATE);

        return user;
    }
}
