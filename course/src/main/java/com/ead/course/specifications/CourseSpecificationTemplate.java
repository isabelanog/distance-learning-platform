package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.UserModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
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

}
