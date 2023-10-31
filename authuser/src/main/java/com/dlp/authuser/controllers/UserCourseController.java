/*
This controller class represents the relationship between user <-> course
 */

package com.dlp.authuser.controllers;

import com.dlp.authuser.clients.CourseClient;
import com.dlp.authuser.dtos.UserCourseDto;
import com.dlp.authuser.models.UserCoursesModel;
import com.dlp.authuser.models.UserModel;
import com.dlp.authuser.service.UserCoursesService;
import com.dlp.authuser.service.UserService;
import com.dlp.authuser.dtos.CourseDto;
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
    public ResponseEntity<Page<CourseDto>> getEnrolledCoursesForUserByUserId(@PageableDefault(sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                            @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getEnrolledCoursesForUserByUserId(userId, pageable));
    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                        @RequestBody @Valid UserCourseDto userCourseDto) {

        Optional<UserModel> user = userService.getUserById(userId);

        if (user.isEmpty()) {
            log.error("User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UUID courseId = userCourseDto.getCourseId();

        if (userCoursesService.isUserSubscribedInCourse(user.get(), courseId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already subscribed in course");
        }

        UserCoursesModel userCoursesModel = userCoursesService.saveSubscription(user.get().convertToUserCourseModel(courseId));

        log.info("User successfully subscribed in course {} ", courseId);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCoursesModel);
    }

    @DeleteMapping("/users/courses/{courseId}")
    public ResponseEntity<Object> deleteCourseByCourseId(@PathVariable(value = "courseId") UUID courseId) {

        if (!userCoursesService.hasUserSubscribedInCourse(courseId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }

        userCoursesService.deleteUserCourseByCourseId(courseId);

        return ResponseEntity.status(HttpStatus.OK).body("UserCourse deleted successfully");
    }

}
