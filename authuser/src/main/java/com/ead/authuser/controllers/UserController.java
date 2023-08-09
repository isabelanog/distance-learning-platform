package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import com.ead.authuser.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
// * permite que esse endpoint seja acessado em qualquer lugar. maxAge = 3600 segundos
@RequestMapping("/users")
public class UserController {
    Logger logger = LogManager.getLogger(AuthenticationController.class);
    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec userSpecification,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<UserModel> userModelPage = userService.findAll(userSpecification, pageable);

        if (!userModelPage.isEmpty()) {
            for (UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getUserById(user.getUserId())).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (userModelOptional.isEmpty()) {
            logger.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            logger.info(userModelOptional.get().toString());
            return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (optionalUserModel.isEmpty()) {
            logger.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            userService.delete(optionalUserModel.get());
            logger.info("User {} deleted successfully", userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody
                                             @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {

        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (optionalUserModel.isEmpty()) {
            logger.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {

            var userModel = optionalUserModel.get();
            userModel.setFullName(userDto.getFullName());
            userModel.setPhoneNumber(userDto.getPhoneNumber());
            userModel.setCpf(userDto.getCpf());
            userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            logger.info("User {} updated successfully", userId);

            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody
                                                 @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {

        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (optionalUserModel.isEmpty()) {
            logger.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (optionalUserModel.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: mismatched old password!");
        } else {

            var userModel = optionalUserModel.get();
            userModel.setPassword(userDto.getPassword());
            userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            logger.info("Password for user {} updated successfully", userId);

            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
        }
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateUserImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody
                                              @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {

        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (optionalUserModel.isEmpty()) {
            logger.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {

            var userModel = optionalUserModel.get();
            userModel.setImageUrl(userDto.getImageUrl());
            userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            logger.info("Image for user {} updated successfully", userId);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
}
