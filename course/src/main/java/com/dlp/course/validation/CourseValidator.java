package com.dlp.course.validation;

import com.dlp.course.dtos.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;
@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object object, Errors errors) {

        CourseDto courseDto = (CourseDto) object;
        validator.validate(courseDto, errors);
        if (!errors.hasErrors()) {
            validateUserInstructor(courseDto.getUserInstructorId(), errors);
        }
    }

    private void validateUserInstructor(UUID userInstructorId, Errors errors) {

//        ResponseEntity<UserDto> response;
//        try {
//            response = authUserClient.getUserById(userInstructorId);
//
//            if (response.getBody().getUserType().equals(UserType.STUDENT)) {
//                errors.rejectValue("userInstructorId", "userInstructorError", "User must be INSTRUCTOR or ADMIN");
//            }
//        } catch (HttpStatusCodeException e) {
//            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
//                errors.rejectValue("userInstructorId", "userInstructorError", "Instructor not found");
//            }
//        }
    }
}
