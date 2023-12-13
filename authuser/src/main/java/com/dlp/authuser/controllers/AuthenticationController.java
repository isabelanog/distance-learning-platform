package com.dlp.authuser.controllers;

import com.dlp.authuser.configs.security.JwtProvider;
import com.dlp.authuser.dtos.JwtDto;
import com.dlp.authuser.dtos.LoginDto;
import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.enums.UserStatus;
import com.dlp.authuser.models.RoleModel;
import com.dlp.authuser.service.RoleService;
import com.dlp.authuser.service.UserService;
import com.dlp.authuser.dtos.UserDto;
import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.models.UserModel;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {

        if (userService.existsByUsername(userDto.getUsername())) {
            logger.error("Error. Username is already taken.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error. Username is already taken.");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            logger.error("Error. Email is already taken.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error. Email is already taken.");
        }
        RoleModel role = roleService.getRole(RoleType.ROLE_STUDENT).orElseThrow( () -> new RuntimeException("Role not found"));

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.getRoles().add(role);

        UserModel user = userService.saveUserAndPublishEvent(userModel);

        logger.info("User {} created successfully", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> userAuthenticator(@Valid @RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.jwtGenerator(authentication);

        return ResponseEntity.ok(new JwtDto(jwt));
    }
}
