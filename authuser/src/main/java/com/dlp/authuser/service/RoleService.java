package com.dlp.authuser.service;

import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.models.RoleModel;

import java.util.Optional;

public interface RoleService {
    Optional<RoleModel> getRole(RoleType roleType);
}
