package com.ead.course.clients;

import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AuthUserClient {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UtilService utilService;

    @Value("${ead.api.url.authuser}")
    String REQUEST_URL_AUTH_USER;
    public Page<UserDto> getUsersByCourse(UUID courseId, Pageable pageable) {

        List<UserDto> userDtoList = null;
        ResponseEntity<ResponsePageDto<UserDto>> responseEntity = null;

        String url = REQUEST_URL_AUTH_USER + utilService.createURLGetCoursesByUser(courseId, pageable);

        log.debug("Request URL : {} ", url);
        log.info("Request URL : {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
            };

            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            userDtoList = responseEntity.getBody().getContent();

            log.debug("Number of elements in Response: {} ", userDtoList.size());

        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {} ", e);
        }

        log.info("Ending request /users userId {} ", courseId);

        return responseEntity.getBody();
    }

    public ResponseEntity<UserDto> getUserById(UUID userId) {
        String url = REQUEST_URL_AUTH_USER + "/users/" +userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }
}
