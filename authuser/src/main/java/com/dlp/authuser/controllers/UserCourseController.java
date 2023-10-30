/*
This controller class represents the relationship between user <-> course
 */

package com.dlp.authuser.controllers;

import com.dlp.authuser.clients.CourseClient;
import com.dlp.authuser.dtos.CourseDto;
import com.dlp.authuser.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    UserService userService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getEnrolledCoursesForUserByUserId(@PageableDefault(sort = "courseId",
            direction = Sort.Direction.ASC) Pageable pageable,
                                                                             @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getEnrolledCoursesForUserByUserId(userId, pageable));
    }

}
