package com.ead.authuser.controllers;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //permite que esse endpoint seja acessado em qualquer lugar. maxAge = 3600 segundos
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

       if (!userModelOptional.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
       } else {
           return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
       }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId) {
        Optional<UserModel> optionalUserModel = userService.findById(userId);

        if (!optionalUserModel.isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found"); }

       else {
           userService.delete(optionalUserModel.get());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully"); }
        }

}
