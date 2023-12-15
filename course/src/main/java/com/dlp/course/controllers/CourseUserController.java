/*
This controller class represents the relationship between course <-> user
 */

package com.dlp.course.controllers;

import com.dlp.course.dtos.SubscriptionDto;
import com.dlp.course.enums.UserStatus;
import com.dlp.course.models.CourseModel;
import com.dlp.course.models.UserModel;
import com.dlp.course.services.CourseService;
import com.dlp.course.services.UserService;
import com.dlp.course.specifications.CourseSpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Object> getUsersSubscribedInCourseByCourseId(CourseSpecificationTemplate.UserSpecification userSpecification,
                                                                       @PageableDefault(sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                       @PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        Specification<UserModel> usersByCourseIdSpecification = CourseSpecificationTemplate.getUsersByCourseIdSpecification(courseId).and(userSpecification);
        Page<UserModel> users = userService.getUsers(usersByCourseIdSpecification, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> saveUserSubscriptionInCourse(@PathVariable UUID courseId,
                                                               @RequestBody @Valid SubscriptionDto subscriptionDto) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        UUID userId = subscriptionDto.getUserId();

        if (courseService.isUserSubscribedInCourse(courseId, userId)) {
            log.error("User already subscribed in course");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already subscribed in course");
        }
        Optional<UserModel> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (user.get().getUserStatus().equals(UserStatus.BLOCKED.toString())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User blocked");
        }

        courseService.saveUserSubscriptionAndSendNotification(course.get(), user.get());

        return ResponseEntity.status(HttpStatus.CREATED).body("User subscribed successfully");
    }
}
