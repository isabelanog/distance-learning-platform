package com.ead.course.controllers;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.dtos.SubscriptionDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUsersService;
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
public class CourseUsersController {

    @Autowired
    AuthUserClient authUserClient;

    @Autowired
    CourseService courseService;

    @Autowired
    CourseUsersService courseUsersService;

    @GetMapping("/courses/{courseId}/users")
    public ResponseEntity<Page<UserDto>> getUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @PathVariable(value = "courseId") UUID courseId) {

        return ResponseEntity.status(HttpStatus.OK).body(authUserClient.getUsersByCourse(courseId, pageable));
    }

    @PostMapping("/courses/{courseId}/users/subscription")
    public ResponseEntity<Object> subscribeUserInCourse(@PathVariable UUID courseId,
                                                           @RequestBody @Valid SubscriptionDto subscriptionDto) {
        UUID userId = subscriptionDto.getUserId();

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course   not found");
        }

        if (courseUsersService.isUserSubscribedToCourse(course.get(), subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User " + userId
                    + " is already subscribed in course " + courseId);
        }

        //TODO: user verification
        CoursesUsersModel coursesUsersModel = course.get().convertToCoursesUsersModel(userId);

        courseUsersService.addUserToCourse(coursesUsersModel);

        return ResponseEntity.status(HttpStatus.OK).body("User " + userId
                + " subscribed in course " + courseId + " successfully");

    }
}
