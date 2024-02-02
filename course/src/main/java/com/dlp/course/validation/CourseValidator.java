package com.dlp.course.validation;

import com.dlp.course.config.security.AuthenticationCurrentUserService;
import com.dlp.course.dtos.CourseDto;
import com.dlp.course.enums.UserType;
import com.dlp.course.models.UserModel;
import com.dlp.course.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.UUID;
@Component
public class CourseValidator implements Validator {

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    @Autowired
    UserService userService;
    @Autowired
    AuthenticationCurrentUserService authenticationCurrentUserService;

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

        UUID currentUserId = authenticationCurrentUserService.getCurrentUser().getUserId();

        if (currentUserId.equals(userInstructorId)) {
            Optional<UserModel> user = userService.getUserById(userInstructorId);

            if (user.isEmpty()) {
                errors.rejectValue("userInstructorId", "userInstructorError", "Instructor not found");
            }
            if (user.get().getUserType().equals(UserType.STUDENT.toString())) {
                errors.rejectValue("userInstructorId", "userInstructorError", "User must be INSTRUCTOR or ADMIN");
            }
        } else {
            throw new AccessDeniedException("Forbidden");
        }
    }
}
