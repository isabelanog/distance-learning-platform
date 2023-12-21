package com.dlp.authuser.service;

import com.dlp.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserModel> findAll();
    Optional<UserModel> getUserById(UUID userId);

    void delete(UserModel user);

    UserModel save(UserModel user);

    boolean isUsernameAlreadyTaken(String username);

    boolean isEmailAlreadyTaken(String email);

    Page<UserModel> getUsers(Specification<UserModel> userSpecification, Pageable pageable);

    void deleteUser(UserModel user);

    UserModel saveUserAndPublishEvent(UserModel user);
    UserModel updateUser(UserModel user);
    UserModel updatePassword(UserModel user);
}
