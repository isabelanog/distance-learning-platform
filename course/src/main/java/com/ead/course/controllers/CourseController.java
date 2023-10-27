package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.CourseUsersService;
import com.ead.course.specifications.CourseSpecificationTemplate;
import com.ead.course.validation.CourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;
    @Autowired
    CourseUsersService courseUsersService;

    @Autowired
    CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> createCourse(@RequestBody CourseDto courseDto, Errors errors) {

        courseValidator.validate(courseDto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        courseService.createCourse(courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> course = courseService.getCourseById(courseId);

        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        courseService.deleteCourse(course.get());

        log.info("Course {} deleted", courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                               @RequestBody @Valid CourseDto courseDto) {
        Optional<CourseModel> courseModelOptional = courseService.getCourseById(courseId);

        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        var courseModel = courseModelOptional.get();
        courseModel.setName(courseDto.getName());
        courseModel.setDescription(courseDto.getDescription());
        courseModel.setImageUrl(courseDto.getImageUrl());
        courseModel.setCourseStatus(courseDto.getCourseStatus());
        courseModel.setCourseLevel(courseDto.getCourseLevel());
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        courseService.createCourse(courseModel);

        return ResponseEntity.status(HttpStatus.OK).body("Course updated successfully");
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getCourses(CourseSpecificationTemplate.CourseSpecification courseSpecification,
                                                        @PageableDefault(sort = "courseId",
                                                                direction = Sort.Direction.ASC) Pageable pageable,
                                                        @RequestParam(required = false) UUID userId) {

        if (userId != null) {

            Page<CourseModel> courses = courseService.getCourses(CourseSpecificationTemplate.getCoursesByUserId(userId).and(courseSpecification), pageable);

            return ResponseEntity.status(HttpStatus.OK).body(courses);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourses(courseSpecification, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getCourseById(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> courseModelOptional = courseService.getCourseById(courseId);

        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
    }
}
