package com.dlp.authuser.dtos;

import com.dlp.authuser.enums.CourseLevel;
import com.dlp.authuser.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
    private UUID userInstructor;

}
