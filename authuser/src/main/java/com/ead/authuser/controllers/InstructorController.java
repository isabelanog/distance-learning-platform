package com.ead.authuser.controllers;

import com.ead.authuser.dtos.InstructorDto;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/subscription")
    public ResponseEntity<Object> instructorSubscription(@RequestBody @Valid InstructorDto instructorDto) {

        UUID userId = instructorDto.getUserId();

        Optional<UserModel> userModelOptional = userService.getUserById(userId);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var instructor = userModelOptional.get();
        instructor.setUserType(UserType.INSTRUCTOR);
        instructor.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(instructor);

        return ResponseEntity.status(HttpStatus.OK).body(instructor);
    }

}
