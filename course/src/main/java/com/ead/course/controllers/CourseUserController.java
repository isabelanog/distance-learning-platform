/*
This controller class represents the relationship between course <-> user
 */

package com.ead.course.controllers;

import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseUserController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getUsersSubscribedInCourseByCourseId(@PageableDefault(size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                       @PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        //TODO: verifications using State Transfer

        return ResponseEntity.status(HttpStatus.CREATED).body("");

    }

}
