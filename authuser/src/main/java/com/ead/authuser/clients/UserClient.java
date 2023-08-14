package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.service.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class UserClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilService utilService;

    String REQUEST_URL_COURSE = "http://localhost:8082";

    public Page<CourseDto> getCoursesByUser(UUID userId, Pageable pageable) {

        ResponseEntity<ResponsePageDto<CourseDto>> responseEntity = null;

        List<CourseDto> courseDtoList;

        String url = REQUEST_URL_COURSE + utilService.createUrlGetCoursesByUser(userId, pageable);

        log.debug("Request URL : {} ", url);
        log.info("Request URL : {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {
            };

            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            courseDtoList = responseEntity.getBody().getContent();

            log.debug("Number of elements in Response: {} ", courseDtoList.size());

        } catch (HttpStatusCodeException e) {
            log.error("Error request / courses {} ", e);
        }

        log.info("Ending request / courses userId {} ", userId);

        return responseEntity.getBody();
    }

}
