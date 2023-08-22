package com.ead.authuser.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "USERS_COURSES")
@IdClass(UserCoursesModel.class)
public class UserCoursesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID id;

    @Type(type = "uuid-char")
    @Column(nullable = false)
    private UUID courseId;

    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @Type(type = "uuid-char")
    private UUID userId;

}
