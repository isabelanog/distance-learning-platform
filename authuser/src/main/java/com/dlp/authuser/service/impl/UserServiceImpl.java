package com.dlp.authuser.service.impl;

import com.dlp.authuser.clients.CourseClient;
import com.dlp.authuser.dtos.UserEventDto;
import com.dlp.authuser.enums.ActionType;
import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.publishers.UserEventPublisher;
import com.dlp.authuser.repositories.UserRepository;
import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.service.UserService;
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

    private final UserRepository userRepository;

    private final UserEventPublisher userEventPublisher;

    public UserServiceImpl(UserRepository userRepository, CourseClient courseClient, UserEventPublisher userEventPublisher) {
        this.userRepository = userRepository;
        this.userEventPublisher = userEventPublisher;
    }

    @Override
    public Optional<UserModel> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    @Override
    public boolean isUsernameAlreadyTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailAlreadyTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> getUsers(Specification<UserModel> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable);
    }
    @Transactional
    @Override
    public void delete(UserModel user) {
        userRepository.delete(user);
    }
    @Transactional
    @Override
    public void deleteUser(UserModel user) {
        delete(user);
        UserEventDto userEventDto = user.convertToUserEventDto();
        userEventPublisher.publishUserEvent(userEventDto, ActionType.DELETE);
    }


    @Transactional
    @Override
    public UserModel saveUserAndPublishEvent(UserModel user) {

        user = userRepository.save(user);
        UserEventDto userEventDto = user.convertToUserEventDto();
        userEventPublisher.publishUserEvent(userEventDto, ActionType.CREATE);

        return user;
    }

    @Override
    public UserModel updateUser(UserModel user) {
        user = userRepository.save(user);
        UserEventDto userEventDto = user.convertToUserEventDto();
        userEventPublisher.publishUserEvent(userEventDto, ActionType.UPDATE);
        return user;
    }

    @Override
    public UserModel updatePassword(UserModel user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserModel> getUserModelByUserType(UserType userType) {
        return userRepository.getUserModelByUserType(userType);
    }
}
