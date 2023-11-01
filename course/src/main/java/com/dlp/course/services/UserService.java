package com.dlp.course.services;


import com.dlp.course.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface UserService {

    Page<UserModel> getUsers(Specification<UserModel> userSpecification, Pageable pageable);

    UserModel save(UserModel userModel);
}
