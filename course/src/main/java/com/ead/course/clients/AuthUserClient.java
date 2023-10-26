/*
The purpose of this class is send data to AuthUser microservice
 */
package com.ead.course.clients;

import com.ead.course.dtos.CourseUserDto;
import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
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

    public Page<UserDto> getUsersSubscribedInCourseByCourseId(UUID courseId, Pageable pageable) {

        List<UserDto> userDtoList = null;
        ResponseEntity<ResponsePageDto<UserDto>> responseEntity = null;

        String url = REQUEST_URL_AUTH_USER + utilService.createURLGetUsersByCourse(courseId, pageable);

        log.debug("Request URL : {} ", url);
        log.info("Request URL : {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
            };

            responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            userDtoList = responseEntity.getBody().getContent();

            log.debug("Number of elements in Response: {} ", userDtoList.size());

        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e.getMessage());
        }

        log.info("Ending request /courses courseId {} ", courseId);

        return new PageImpl<>(userDtoList);
    }

    public ResponseEntity<UserDto> getUserById(UUID userId) {
        String url = REQUEST_URL_AUTH_USER + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserDto.class);
    }

    public void sendUserSubscription(UUID courseId, UUID userId) {

        String url = REQUEST_URL_AUTH_USER + "/users/" + userId + "/courses/subscription";

        var courseUserDto = new CourseUserDto();
        courseUserDto.setCourseId(courseId);
        courseUserDto.setUserId(userId);
        try {
            restTemplate.postForObject(url, courseUserDto, String.class);
        } catch (Exception e) {
            log.error("Error in send user subscription to Course Microservice");
            throw new RestClientException(e.getMessage());
        }
    }

    public void deleteCourseInAuthUser(UUID courseId) {
        String url = REQUEST_URL_AUTH_USER + "/users/courses/" + courseId ;
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}
