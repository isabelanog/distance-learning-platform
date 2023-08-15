package com.ead.course.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {
    public String createURL(UUID courseId, Pageable pageable);
}
