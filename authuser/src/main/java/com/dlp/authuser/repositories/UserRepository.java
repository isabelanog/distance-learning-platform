package com.dlp.authuser.repositories;

import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.models.UserModel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> getUserModelByUsername(String username);
    @EntityGraph(attributePaths = "roles", type = EntityGraph.EntityGraphType.FETCH)
    Optional<UserModel> findById(UUID userId);
    List<UserModel> getUserModelByUserType(UserType userType);
}
