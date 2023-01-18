package com.dihastro.santa;

import com.dihastro.santa.exception.UserAlreadyExistsException;
import com.dihastro.santa.model.User;
import com.dihastro.santa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/create")
    User createUser(@RequestBody User newUser) {
        try {
            return userRepository.save(newUser);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException(newUser.getUsername());
        }
    }

}
