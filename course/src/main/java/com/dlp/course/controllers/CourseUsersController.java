/*
This controller class represents the relationship between course <-> user
 */

package com.dlp.course.controllers;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.CoursesUsersModel;
import com.dlp.course.services.CourseService;
import com.dlp.course.services.CourseUsersService;
import com.dlp.course.clients.AuthUserClient;
import com.dlp.course.dtos.SubscriptionDto;
import com.dlp.course.dtos.UserDto;
import com.dlp.course.enums.UserStatus;
import com.dlp.course.services.exception.ResponseHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUsersController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUsersService courseUsersService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getUsersSubscribedInCourseByCourseId(@PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getUsersSubscribedInCourseByCourseId(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                        @RequestBody @Valid SubscriptionDto subscriptionDto) {
        UUID userId = subscriptionDto.getUserId();
        ResponseEntity<UserDto> userResponse;

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        if (courseUsersService.isUserSubscribedToCourse(course.get(), userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("This user is already subscribed");
        }

        try {
            userResponse = authUserClient.getUserById(userId);

            if (userResponse.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseHandler.generateResponse("User blocked", HttpStatus.CONFLICT, null);
            }

        } catch (HttpStatusCodeException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseHandler.generateResponse("User not found", HttpStatus.NOT_FOUND, null);
            }
        }
        CoursesUsersModel coursesUsersModel = courseUsersService.saveAndSendUserSubscriptionInCourseToAuthUserMicroservice(course.get().convertToCoursesUsersModel(userId));

        return ResponseEntity.status(HttpStatus.CREATED).body(coursesUsersModel);

    }
    @DeleteMapping("/courses/users/{userId}")
    public ResponseEntity<Object> deleteCourseUserByUserId(@PathVariable(value = "userId") UUID userId) {
        //detele row in course_user table

        if (!courseUsersService.getCourseUserByUserId(userId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserCourse not found");
        }
        courseUsersService.deleteCourseUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body("CourseUser deleted successfully");
    }
}
