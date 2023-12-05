package com.dlp.authuser.repositories;

import com.dlp.authuser.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
}
