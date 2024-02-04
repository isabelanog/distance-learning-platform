package com.dlp.authuser.controllers;

import com.dlp.authuser.enums.RoleType;
import com.dlp.authuser.models.RoleModel;
import com.dlp.authuser.service.RoleService;
import com.dlp.authuser.service.UserService;
import com.dlp.authuser.dtos.InstructorDto;
import com.dlp.authuser.enums.UserType;
import com.dlp.authuser.models.UserModel;
import io.swagger.annotations.Api;
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
@Api(tags = "Instructor")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructor")
public class InstructorController {

    private final UserService userService;

    private final RoleService roleService;

    public InstructorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Object> getInstructors() {

        if (userService.getUserModelByUserType(UserType.INSTRUCTOR).isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There isn't instructors");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserModelByUserType(UserType.INSTRUCTOR));
    }
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
