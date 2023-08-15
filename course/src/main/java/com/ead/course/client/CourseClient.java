package com.ead.course.client;

import com.ead.course.dtos.ResponsePageDto;
import com.ead.course.dtos.UserDto;
import com.ead.course.services.UtilService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UtilService utilService;


    public Page<UserDto> getUsersByCourse(UUID courseId, Pageable pageable) {

        List<UserDto> userDtoList = null;

        String url = utilService.createURL(courseId, pageable);

        log.debug("Request URL : {} ", url);
        log.info("Request URL : {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDto<UserDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<UserDto>>() {
            };

            ResponseEntity<ResponsePageDto<UserDto>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            userDtoList = responseEntity.getBody().getContent();

            log.debug("Number of elements in Response: {} ", userDtoList.size());

        } catch (HttpStatusCodeException e) {
            log.error("Error request / courses {} ", e);
        }

        log.info("Ending request / courses userId {} ", courseId);

        return new PageImpl<>(userDtoList);
    }
}
