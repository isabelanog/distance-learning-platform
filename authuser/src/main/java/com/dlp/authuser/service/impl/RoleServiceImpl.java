package com.dlp.authuser.service.impl;

import com.dlp.authuser.repositories.RoleRepository;
import com.dlp.authuser.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {


    @Autowired
    RoleRepository roleRepository;
}
