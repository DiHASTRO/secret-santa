package com.dihastro.santa.exception;

public class UserNotFoundException  extends RuntimeException {

    UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}
