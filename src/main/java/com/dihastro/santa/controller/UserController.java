package com.dihastro.santa.controller;

import com.dihastro.santa.exception.UserAlreadyExistsException;
import com.dihastro.santa.model.Response;
import com.dihastro.santa.model.User;
import com.dihastro.santa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            userRepository.save(newUser);
            return new ResponseEntity<>(Response.OK, HttpStatus.OK);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException(newUser.getUsername());
        }
    }
}
