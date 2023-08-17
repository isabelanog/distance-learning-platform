package com.ead.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {
    String createURLGetCoursesByUser(UUID courseId, Pageable pageable);
}
