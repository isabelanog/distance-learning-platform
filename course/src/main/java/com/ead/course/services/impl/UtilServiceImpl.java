package com.ead.course.services.impl;

import com.ead.course.services.UtilService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    public String createURLGetUsersByCourse(UUID courseId, Pageable pageable) {

        return "/users?courseId=" + courseId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
