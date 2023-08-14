package com.ead.authuser.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilService {
    public String createURL(UUID userId, Pageable pageable);
}
