package com.dlp.course.services.impl;

import com.dlp.course.dtos.NotificationCommandDto;
import com.dlp.course.models.LessonModel;
import com.dlp.course.models.ModuleModel;
import com.dlp.course.models.UserModel;
import com.dlp.course.publishers.NotificationCommandPublisher;
import com.dlp.course.repositories.CourseRepository;
import com.dlp.course.repositories.ModuleRepository;
import com.dlp.course.services.CourseService;
import com.dlp.course.models.CourseModel;
import com.dlp.course.repositories.UserRepository;
import com.dlp.course.repositories.LessonRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Log4j2
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void deleteCourse(CourseModel course) {
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
        courseRepository.deleteCourseUserByCourseId(courseId.toString());
        courseRepository.delete(course);
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

    @Override
    public boolean isUserSubscribedInCourse(UUID courseId, UUID userId) {
        int i = courseRepository.existsByCourseAndUser(courseId, userId);
        if (i == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public void saveUserSubscription(UUID courseId, UUID userId) {

        courseRepository.subscribeUser(courseId.toString(), userId.toString());
    }

    @Transactional
    @Override
    public void saveUserSubscriptionAndSendNotification(CourseModel course, UserModel user) {

        saveUserSubscription(course.getCourseId(), user.getUserId());

        try {
            var notificationCommandDto = new NotificationCommandDto();
            notificationCommandDto.setTitle("Welcome to " + course.getName() + " course");
            notificationCommandDto.setMessage(user.getFullName() + " you are subscribed successfully!");
            notificationCommandDto.setUserId(user.getUserId());

            notificationCommandPublisher.publishNotificationCommand(notificationCommandDto);
        } catch (Exception e) {
            log.error("Error to send notification");
        }
    }
}
