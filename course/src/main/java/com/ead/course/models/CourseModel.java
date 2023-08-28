package com.ead.course.models;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "COURSES")
public class CourseModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    @Type(type = "uuid-char")
    private UUID userInstructorId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ModuleModel> modules;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    private Set<CoursesUsersModel> coursesUsersModels;


    public CourseModel() {
    }

    public CourseModel(UUID courseId, String name, String description, String imageUrl, LocalDateTime creationDate,
                       LocalDateTime lastUpdateDate, CourseStatus courseStatus, CourseLevel courseLevel,
                       UUID userInstructor, Set<ModuleModel> modules, Set<CoursesUsersModel> coursesUsersModels) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.courseStatus = courseStatus;
        this.courseLevel = courseLevel;
        this.userInstructorId = userInstructor;
        this.modules = modules;
        this.coursesUsersModels = coursesUsersModels;
    }

    public CoursesUsersModel convertToCoursesUsersModel(UUID userId) {
        return new CoursesUsersModel(null, userId, this);
    }

    public void setCoursesUsersModels(CoursesUsersModel coursesUsersModels) {
        this.coursesUsersModels.add(coursesUsersModels);
    }
    public Set<CoursesUsersModel> getCoursesUsersModels() {
        return coursesUsersModels;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    public UUID getUserInstructorId() {
        return userInstructorId;
    }

    public void setUserInstructorId(UUID userInstructor) {
        this.userInstructorId = userInstructor;
    }

    public Set<ModuleModel> getModules() {
        return modules;
    }

    public void setModules(Set<ModuleModel> modules) {
        this.modules = modules;
    }
}
