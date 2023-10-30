package com.ead.authuser.service;

import com.ead.authuser.models.UserModel;
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

    void save(UserModel user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Page<UserModel> getUsers(Specification<UserModel> userSpecification, Pageable pageable);

    void deleteUser(UserModel user);

}
