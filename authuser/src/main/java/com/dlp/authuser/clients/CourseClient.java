/*
The purpose of this class is sent data to Course microservice
 */
package com.dlp.authuser.clients;

import com.dlp.authuser.dtos.CourseDto;
import com.dlp.authuser.dtos.ResponsePageDto;
import com.dlp.authuser.service.UtilService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilService utilService;

    @Value("${dlp.api.url.course}")
    String REQUEST_URL_COURSE;

    //@Retry(name = "retryInstance", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "circuitBreakInstance")
    public Page<CourseDto> getEnrolledCoursesForUserByUserId(UUID userId, Pageable pageable, String token) {

        List<CourseDto> courseDtoList = null;
        ResponseEntity<ResponsePageDto<CourseDto>> responseEntity = null;

        String url = REQUEST_URL_COURSE + utilService.createURLGetCoursesByUser(userId, pageable);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", headers);

        log.debug("Request URL : {} ", url);
        log.info("Request URL : {} ", url);

        ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
        };

        responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType);

        courseDtoList = responseEntity.getBody().getContent();

        log.debug("Number of elements in Response: {} ", courseDtoList.size());


        log.info("Ending request /courses userId {} ", userId);

        return responseEntity.getBody();
    }

    public Page<CourseDto> circuitBreakerFallback(UUID userId, Pageable pageable, Throwable throwable) {
        log.error("Error in circuit breaker fallback {}", throwable.toString());
        List<CourseDto> courseDtoList = new ArrayList<>();
        return new PageImpl<>(courseDtoList);
    }

    public Page<CourseDto> retryFallback(UUID userId, Pageable pageable, Throwable throwable) {
        log.error("Error in retry fallback {}", throwable.toString());
        List<CourseDto> courseDtoList = new ArrayList<>();
        return new PageImpl<>(courseDtoList);
    }
}
