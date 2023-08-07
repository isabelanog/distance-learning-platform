package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    @Autowired
    LessonService lessonService;

    @Autowired
    ModuleService moduleService;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                             @RequestBody @Valid LessonDto lessonDto) {

        Optional<ModuleModel> moduleModelOptional = moduleService.findModuleById(moduleId);

        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found");
        }
        var lessonModule = new LessonModel();
        BeanUtils.copyProperties(lessonDto, lessonModule);
        lessonModule.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModule.setModule(moduleModelOptional.get());
        lessonService.save(lessonModule);
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonModule);
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

        if (lessonModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found in this module");
        }
        lessonService.delete(lessonModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> uptadeLesson(@PathVariable(value = "moduleId") UUID moduleId,
                                               @PathVariable(value = "lessonId") UUID lessonId,
                                               @RequestBody @Valid LessonDto lessonDto) {

        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

        if (lessonModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found in this module");
        }
        var lessonModel = lessonModelOptional.get();
        lessonModel.setTitle(lessonDto.getTitle());
        lessonModel.setDescription(lessonDto.getDescription());
        lessonModel.setVideoUrl(lessonDto.getVideoUrl());

        lessonService.save(lessonModel);

        return ResponseEntity.status(HttpStatus.OK).body("Lesson updated successfully");
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId) {

        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAllLessonsIntoModule(moduleId));

    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getLessonById(@PathVariable(value = "moduleId") UUID moduleId,
                                                @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<LessonModel> lessonModelOptional = lessonService.findLessonIntoModule(moduleId, lessonId);

        if (lessonModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found in this module");
        }

        return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional);
    }

}
