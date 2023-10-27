package com.ead.authuser.specification;

import com.ead.authuser.models.UserCoursesModel;
import com.ead.authuser.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class UserSpecificationTemplate {
    @And({
        @Spec(path = "userType", spec = Equal.class),
        @Spec(path = "userStatus", spec = Equal.class),
        @Spec(path = "email", spec = Like.class),
        @Spec(path = "fullName", spec = Like.class)
        })
    public interface UserSpecification extends Specification<UserModel> {

    }

    public static Specification<UserModel> getUsersByCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Join<UserModel, UserCoursesModel> modelJoin = root.join("usersCourses");
            return criteriaBuilder.equal(modelJoin.get("courseId"), courseId);
        };
    }
}
