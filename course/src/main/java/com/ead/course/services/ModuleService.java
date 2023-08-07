package com.ead.course.services;

import com.ead.course.models.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ModuleService {

    void deleteModule(ModuleModel moduleModel);

    ModuleModel save(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<ModuleModel> findAllModulesByCourse(UUID courseId);

    Optional<ModuleModel> findModuleById(UUID moduleId);

    Page<ModuleModel> getModulesByCourseId(Specification<ModuleModel> modelSpecification, Pageable pageable);

    Page<ModuleModel> findAllModulesByCourse(Specification<ModuleModel> moduleSpecification, Pageable pageable);

}
