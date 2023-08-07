package com.ead.course.services;

import com.ead.course.models.ModuleModel;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface ModuleService {

    void deleteModule(ModuleModel moduleModel);

    ModuleModel save(ModuleModel moduleModel);

    Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId);

    List<ModuleModel> findAllModulesByCourse(UUID courseId);

    Optional<ModuleModel> findModuleById(UUID moduleId);

}
