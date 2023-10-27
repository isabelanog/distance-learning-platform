package com.ead.course.services.impl;

import com.ead.course.clients.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CoursesUsersModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.CourseRepository;
import com.ead.course.repositories.CourseUsersRepository;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    CourseUsersRepository courseUsersRepository;

    @Autowired
    AuthUserClient authUserClient;

    @Transactional
    @Override
    public void deleteCourse(CourseModel course) {
        boolean hasUserSubscribedInCourse = false;
        UUID courseId = course.getCourseId();

        List<ModuleModel> modules = moduleRepository.findAllModulesIntoCourse(courseId);

        if (!modules.isEmpty()) {
            for (ModuleModel module : modules) {

                List<LessonModel> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());

                if (!lessons.isEmpty()) {
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }

        List<CoursesUsersModel> coursesUsersModelList = courseUsersRepository.getAllByCourse_CourseId(courseId);
        if (!coursesUsersModelList.isEmpty()) {
            courseUsersRepository.deleteAll(coursesUsersModelList);
            hasUserSubscribedInCourse = true;
        }
        courseRepository.delete(course);

        if (hasUserSubscribedInCourse) {
            authUserClient.deleteCourseInAuthUser(courseId);
        }
    }

    @Override
    public CourseModel createCourse(CourseModel courseModel) {
        return courseRepository.save(courseModel);
    }

    @Override
    public Optional<CourseModel> getCourseById(UUID courseId) {
       return courseRepository.findById(courseId);
    }

    @Override
    public Page<CourseModel> getCourses(Specification<CourseModel> courseSpecification, Pageable pageable) {
        return courseRepository.findAll(courseSpecification, pageable);
    }
}
