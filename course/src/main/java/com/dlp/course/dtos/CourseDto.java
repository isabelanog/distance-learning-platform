package com.dlp.course.dtos;

import com.dlp.course.enums.CourseLevel;
import com.dlp.course.enums.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CourseDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    private String imageUrl;

    @NotNull(message = "Status cannot be null")
    private CourseStatus courseStatus;

    @NotNull(message = "User id cannot be null")
    private UUID userInstructorId;

    @NotNull(message = "Course level cannot be null")
    private CourseLevel courseLevel;

}
