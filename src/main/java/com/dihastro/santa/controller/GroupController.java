package com.dihastro.santa.controller;

import com.dihastro.santa.model.*;
import com.dihastro.santa.repo.GroupRepository;
import com.dihastro.santa.repo.UserRepository;
import com.dihastro.santa.repo.UserToGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class GroupController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserToGroupRepository userToGroupRepository;

    @PostMapping("/group/create")
    ResponseEntity<Response> createGroup(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group newGroup = new Group(req.getGroupname());
        // List<User> users = userRepository.findByUsername(req.getExecutor());
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        groupRepository.save(newGroup);
        userToGroupRepository.save(new UserToGroup(newGroup, user, GroupRole.ADMIN));
        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @PostMapping("/group/join")
    ResponseEntity<Response> joinGroup(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        User user = userRepository.getByUsername(req.getExecutor());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }

        userToGroupRepository.save(new UserToGroup(group, user, GroupRole.MEMBER));

        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @GetMapping("/group/list")
    ResponseEntity<List<String>> listGroup() {
        List<String> list = new LinkedList<>();
        for (Group group: groupRepository.findAll()) {
            list.add(group.getGroupname());
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // @PostMapping("/group/add_")
}
