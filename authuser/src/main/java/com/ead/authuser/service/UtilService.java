package com.ead.authuser.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {
    String createURLGetCoursesByUser(UUID userId, Pageable pageable);
}
