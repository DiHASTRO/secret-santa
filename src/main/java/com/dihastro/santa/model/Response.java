package com.dihastro.santa.model;

import jakarta.annotation.Nullable;

public class Response {
    public static final Response OK = new Response((short) 0, "Ok");
    public static final Response BAD_ARGUMENTS = new Response((short) 1, "Bad arguments");
    public static final Response NON_EXISTENT_USER = new Response((short) 2, "User does not exists");
    public static final Response NON_EXISTENT_GROUP = new Response((short) 3, "Group does not exists");

    private Short code;
    private String message;

    public Response(Short code, String message) {
        this.code = code;
        this.message = message;
    }
    public Short getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
