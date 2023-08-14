package com.ead.authuser.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {
    public String createUrlGetCoursesByUser(UUID userId, Pageable pageable);
}
