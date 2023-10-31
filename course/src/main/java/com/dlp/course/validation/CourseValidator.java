package com.dlp.course.validation;

import com.dlp.course.clients.AuthUserClient;
import com.dlp.course.dtos.CourseDto;
import com.dlp.course.dtos.UserDto;
import com.dlp.course.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;


@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    AuthUserClient authUserClient;

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

        ResponseEntity<UserDto> response;
        try {
            response = authUserClient.getUserById(userInstructorId);

            if (response.getBody().getUserType().equals(UserType.STUDENT)) {
                errors.rejectValue("userInstructorId", "userInstructorError", "User must be INSTRUCTOR or ADMIN");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                errors.rejectValue("userInstructorId", "userInstructorError", "Instructor not found");
            }
        }
    }
}
