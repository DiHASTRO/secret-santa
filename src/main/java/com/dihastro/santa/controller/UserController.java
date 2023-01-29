package com.dihastro.santa.controller;

import com.dihastro.santa.model.Response;
import com.dihastro.santa.model.User;
import com.dihastro.santa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
    ResponseEntity<Response> createUser(@RequestBody User newUser) {
        if (newUser.getUsername() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        if (userRepository.getByUsername(newUser.getUsername()) != null) {
            return new ResponseEntity<>(Response.TAKEN_USERNAME, HttpStatus.EXPECTATION_FAILED);
        }
        userRepository.save(newUser);
        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }
}
