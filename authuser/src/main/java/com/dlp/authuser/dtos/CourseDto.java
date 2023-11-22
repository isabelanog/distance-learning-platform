package com.dlp.authuser.dtos;

import com.dlp.authuser.enums.CourseLevel;
import com.dlp.authuser.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface ImagePut {}

    }

    private UUID courseId;

    @NotBlank(groups = CourseDto.UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = {UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class},
            message = "Name size must be between 4 and 50")
    @JsonView({UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    private String name;

    @NotBlank(groups = CourseDto.UserView.RegistrationPost.class)
    @Size(min = 50, max = 100, groups = {UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class},
            message = "Description size must be between 50 and 100")
    @JsonView({UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    private String description;

    @NotBlank(groups = UserDto.UserView.ImagePut.class)
    @JsonView(UserDto.UserView.ImagePut.class)
    private String imageUrl;

    @NotBlank(groups = CourseDto.UserView.RegistrationPost.class)
    @Size(min = 4, max = 10, groups = {UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class},
            message = "Course status size must be between 4 and 50")
    @JsonView({UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    private CourseStatus courseStatus;

    @NotBlank(groups = CourseDto.UserView.RegistrationPost.class)
    @Size(min = 4, max = 10, groups = {UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class},
            message = "Course level size must be between 4 and 50")
    @JsonView({UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    private CourseLevel courseLevel;

    @NotBlank(groups = CourseDto.UserView.RegistrationPost.class, message = "User instructor id must not be blank")
    @JsonView({UserDto.UserView.RegistrationPost.class, UserDto.UserView.UserPut.class})
    private UUID userInstructor;



}
