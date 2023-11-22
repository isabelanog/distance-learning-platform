package com.dlp.course.dtos;

import com.dlp.course.enums.CourseLevel;
import com.dlp.course.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDto {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Description must not be blank")
    private String description;

    private String imageUrl;

    @NotNull(message = "Course status must not be blank")
    private CourseStatus courseStatus;

    @NotNull(message = "User instructor id  must not be blank")
    private UUID userInstructorId;

    @NotNull(message = "Course level must not be blank")
    private CourseLevel courseLevel;

}
