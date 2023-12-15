package com.dlp.authuser.controllers;

import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.models.RoleModel;
import com.dlp.authuser.service.RoleService;
import com.dlp.authuser.service.UserService;
import com.dlp.authuser.dtos.InstructorDto;
import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructor")
public class InstructorController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/subscription")
    public ResponseEntity<Object> instructorSubscription(@RequestBody @Valid InstructorDto instructorDto) {

        UUID userId = instructorDto.getUserId();

        Optional<UserModel> userModelOptional = userService.getUserById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        RoleModel role = roleService.getRole(RoleType.ROLE_INSTRUCTOR).orElseThrow(() -> new RuntimeException("Role not found"));

        var instructor = userModelOptional.get();
        instructor.setUserType(UserType.INSTRUCTOR);
        instructor.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        instructor.getRoles().add(role);

        userService.updateUser(instructor);

        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }
}
