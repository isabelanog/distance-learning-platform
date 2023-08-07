package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
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
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    LessonRepository lessonRepository;
    @Transactional
    @Override
    public void deleteModule(ModuleModel moduleModel) {
        List<LessonModel> lessonModelList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());

        if (!lessonModelList.isEmpty()) {
            lessonRepository.deleteAll(lessonModelList);
        }
        moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) {
        return moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<ModuleModel> findAllModulesByCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<ModuleModel> findModuleById(UUID moduleId) {
        return moduleRepository.findById(moduleId);
    }

    @Override
    public Page<ModuleModel> getModulesByCourseId(Specification<ModuleModel> modelSpecification, Pageable pageable) {
        return moduleRepository.findAll(modelSpecification, pageable);
    }

    @Override
    public Page<ModuleModel> findAllModulesByCourse(Specification<ModuleModel> moduleSpecification, Pageable pageable) {
        return moduleRepository.findAll(moduleSpecification, pageable);
    }
}
