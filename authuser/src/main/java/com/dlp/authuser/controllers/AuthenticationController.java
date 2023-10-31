package com.dlp.authuser.controllers;

import com.dlp.authuser.dtos.UserDto;
import com.dlp.authuser.enums.UserStatus;
import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                               @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {

        if (userService.existsByUsername(userDto.getUsername())) {
            logger.warn("Error. Username is already taken.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error. Username is already taken.");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            logger.warn("Error. Email is already taken.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error. Email is already taken.");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);
        logger.info("User {} created successfully", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
