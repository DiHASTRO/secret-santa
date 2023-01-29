package com.dihastro.santa.model;

public class Response {
    public static final Response OK = new Response((short) 0, "Ok");
    public static final Response BAD_ARGUMENTS = new Response((short) 1, "Bad arguments");
    public static final Response NON_EXISTENT_USER = new Response((short) 2, "User does not exists");
    public static final Response NON_EXISTENT_GROUP = new Response((short) 3, "Group does not exists");
    public static final Response TAKEN_USERNAME = new Response((short) 4, "This username is already taken");
    public static final Response NOT_IN_GROUP = new Response((short) 5, "User is not in this group");
    public static final Response NO_RIGHTS = new Response((short) 6, "Executor does not have enough rights");
    public static final Response NOT_ENOUGH_MEMBERS = new Response((short) 7, "Group does not have enough members");
    public static final Response ALREADY_IN_GROUP = new Response((short) 8, "User is already in this group");
    public static final Response GROUP_NOT_CLOSED = new Response((short) 9, "Secret Santa in this group is not started yet");
    public static final Response GROUP_ALREADY_CLOSED = new Response((short) 10, "Secret Santa in this group is already started");
    public static final Response TAKEN_GROUP_NAME = new Response((short) 11, "Group with this name is already exists");
    public static final Response NOT_ENOUGH_ADMINS = new Response((short) 12, "You must have left at least one admin before leaving");

    private final Short code;
    private final String message;

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
