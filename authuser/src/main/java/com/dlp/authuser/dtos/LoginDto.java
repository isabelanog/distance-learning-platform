package com.dlp.authuser.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank(message = "Username must not be empty")
    private String username;

    @NotBlank(message = "Password must not be empty")
    private String password;
}
