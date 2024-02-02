package com.dlp.authuser.service.impl;

import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.models.RoleModel;
import com.dlp.authuser.repositories.RoleRepository;
import com.dlp.authuser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<RoleModel> getRole(RoleType roleType) {
        return roleRepository.findByRoleType(roleType);
    }
}
