package com.dlp.authuser.repositories;

import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByRoleType(RoleType roleType);
}
