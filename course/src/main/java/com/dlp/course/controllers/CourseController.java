package com.dlp.course.controllers;

import com.dlp.course.dtos.CourseDto;
import com.dlp.course.services.CourseService;
import com.dlp.course.services.UserService;
import com.dlp.course.validation.CourseValidator;
import com.dlp.course.models.CourseModel;
import com.dlp.course.specifications.CourseSpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    CourseValidator courseValidator;
    @PostMapping
    public ResponseEntity<Object> postCourse(@RequestBody CourseDto courseDto, Errors errors) {

        courseValidator.validate(courseDto, errors);

        if (errors.hasErrors()) {

            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }

        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        courseService.createCourse(courseModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseModel);
    }
    @GetMapping
    public ResponseEntity<Page<CourseModel>> getCourses(CourseSpecificationTemplate.CourseSpecification courseSpecification,
                                                        @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                        @RequestParam(required = false) UUID userId) {
        if (userId != null) {

            Specification<CourseModel> courseModelSpecification = CourseSpecificationTemplate.getCoursesByUserIdSpecification(userId).and(courseSpecification);
            Page<CourseModel> courses = courseService.getCourses(courseModelSpecification, pageable);

            return ResponseEntity.status(HttpStatus.OK).body(courses);

        } else {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourses(courseSpecification, pageable));
        }
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

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getCourseById(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseModel> courseModelOptional = courseService.getCourseById(courseId);

        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
    }
}
