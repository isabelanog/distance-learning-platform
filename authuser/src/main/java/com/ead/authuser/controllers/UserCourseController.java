package com.ead.authuser.controllers;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.UserCourseDto;
import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserCoursesService;
import com.ead.authuser.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserService userService;

    @Autowired
    UserCoursesService userCoursesService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                            @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getCoursesByUser(userId, pageable));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> subscribeUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                        @RequestBody @Valid UserCourseDto userCourseDto) {

        Optional<UserModel> userModelOptional = userService.getUserById(userId);

        if (userModelOptional.isEmpty()) {
            log.warn("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UUID courseId = userCourseDto.getCourseId();

        if (userCoursesService.isUserSubscribedInCourse(userModelOptional.get(), courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User " + userId
                    + " is already subscribed in course " + courseId);
        }

        UserCoursesModel userCoursesModel = userCoursesService.addUserToCourse(userModelOptional.get().convertToUserCourseModel(courseId));

        log.info("Instructor created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(userCoursesModel);
    }
}
