package com.dlp.course.specifications;

import com.dlp.course.models.CourseModel;
import com.dlp.course.models.LessonModel;
import com.dlp.course.models.ModuleModel;
import com.dlp.course.models.UserModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class CourseSpecificationTemplate {
    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpecification extends Specification<CourseModel> {

    }
    @And({
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "userType", spec = Equal.class)
    })
    public interface UserSpecification extends Specification<UserModel> {

    }
    @Spec(path = "title", spec = Like.class)
    public interface ModuleSpecification extends Specification<ModuleModel> {}
    @Spec(path = "title", spec = Like.class)
    public interface LessonSpecification extends Specification<LessonModel> {}
    public static Specification<ModuleModel> modulesByCourseIdSpecification(final UUID courseId) {
        //vincula uma pesquisa um ID aos filtros de specification
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleModel> moduleModelRoot = root; // A entity
            Root<CourseModel> courseModelRoot = query.from(CourseModel.class); // B entity
            Expression<Collection<ModuleModel>> coursesModules = courseModelRoot.get("modules");

            return criteriaBuilder.and(criteriaBuilder.equal(courseModelRoot.get("courseId"), courseId),
                                                criteriaBuilder.isMember(moduleModelRoot, coursesModules));

        };
    }

    public static Specification<LessonModel> lessonByModuleIdSpecification(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonModel> lessonModelRoot = root; // A entity
            Root<ModuleModel> moduleModelRoot = query.from(ModuleModel.class); // B entity
            Expression<Collection<LessonModel>> lessonsModules = moduleModelRoot.get("lessons");

            return criteriaBuilder.and(criteriaBuilder.equal(moduleModelRoot.get("moduleId"), moduleId),
                    criteriaBuilder.isMember(lessonModelRoot, lessonsModules));

        };
    }
    public static Specification<UserModel> getUsersByCourseIdSpecification(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<UserModel> user = root; // A entity
            Root<CourseModel> course = query.from(CourseModel.class); // B entity
            Expression<Collection<UserModel>> coursesUsers = course.get("users");

            return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(user, coursesUsers));
        };
    }
    public static Specification<CourseModel> getCoursesByUserIdSpecification(final UUID userId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<CourseModel> course = root;
            Root<UserModel> user = query.from(UserModel.class);
            Expression<Collection<CourseModel>> usersCourses = user.get("courses");

            return criteriaBuilder.and(criteriaBuilder.equal(user.get("userId"), userId),
                    criteriaBuilder.isMember(course, usersCourses));
        };
    }

}
