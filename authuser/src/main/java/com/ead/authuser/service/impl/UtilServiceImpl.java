package com.ead.authuser.service.impl;

import com.ead.authuser.service.UtilService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class UtilServiceImpl implements UtilService {

    public String createURLGetCoursesByUser(UUID userId, Pageable pageable) {

        return "/courses?userId=" + userId + "&page=" + pageable.getPageNumber()
                + "&size=" + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}
