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

import java.util.*;

@RestController
public class GroupController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserToGroupRepository userToGroupRepository;

    private long countAdmins(Group group) {
        Set<UserToGroup> members = group.getMembers();
        return members.stream().filter(member -> member.getRole().equals(GroupRole.ADMIN)).count();
    }

    @PostMapping("/group/create")
    ResponseEntity<Response> createGroup(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        if (groupRepository.getByGroupname(req.getGroupname()) != null) {
            return new ResponseEntity<>(Response.TAKEN_GROUP_NAME, HttpStatus.EXPECTATION_FAILED);
        }
        Group newGroup = new Group(req.getGroupname());
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
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        if (userToGroupRepository.getByGroupAndUser(group, user) != null) {
            return new ResponseEntity<>(Response.ALREADY_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        userToGroupRepository.save(new UserToGroup(group, user, GroupRole.MEMBER));

        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @GetMapping("/list-groups")
    ResponseEntity<Map<String, String>> listGroup() {
        Map<String, String> map = new HashMap<>();
        for (Group group: groupRepository.findAll()) {
            map.put(group.getGroupname(), group.getClosed() ? "Closed": "Opened");
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/group/add-wish")
    ResponseEntity<Response> addWish(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null || req.getOperand() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup link = userToGroupRepository.getByGroupAndUser(group, user);
        if (link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        link.setWish(req.getOperand());
        userToGroupRepository.save(link);
        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @PostMapping("/group/admin-appointment")
    ResponseEntity<Response> appointAdmin(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null || req.getOperand() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup exec_link = userToGroupRepository.getByGroupAndUser(group, user);
        if (exec_link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup oper_link = userToGroupRepository.getByGroupAndUser(group, user);
        if (oper_link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (exec_link.getRole() != GroupRole.ADMIN) {
            return new ResponseEntity<>(Response.NO_RIGHTS, HttpStatus.EXPECTATION_FAILED);
        }
        oper_link.setRole(GroupRole.ADMIN);

        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @PostMapping("/group/start-secret-santa")
    ResponseEntity<Response> startSanta(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup link = userToGroupRepository.getByGroupAndUser(group, user);
        if (link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (link.getRole() != GroupRole.ADMIN) {
            return new ResponseEntity<>(Response.NO_RIGHTS, HttpStatus.EXPECTATION_FAILED);
        }
        LinkedList<UserToGroup> members = new LinkedList<>(group.getMembers());
        if (members.size() < 3) {
            return new ResponseEntity<>(Response.NOT_ENOUGH_MEMBERS, HttpStatus.EXPECTATION_FAILED);
        }
        Collections.shuffle(members);

        for (int i = 0; i < members.size() - 1; ++i) {
            UserToGroup utg = members.get(i);
            UserToGroup utgToChange = userToGroupRepository.getByGroupAndUser(utg.getGroup(), utg.getUser());
            utgToChange.setToGift(members.get(i + 1).getUser());
            userToGroupRepository.save(utgToChange);
        }
        UserToGroup utg = members.get(members.size() - 1);
        UserToGroup utgToChange = userToGroupRepository.getByGroupAndUser(utg.getGroup(), utg.getUser());
        utgToChange.setToGift(members.get(0).getUser());
        userToGroupRepository.save(utgToChange);

        group.setClosed(true);
        groupRepository.save(group);

        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @PostMapping("/group/get-to-who")
    ResponseEntity<?> getToWho(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (!group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_NOT_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup link = userToGroupRepository.getByGroupAndUser(group, user);
        if (link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        Map<String, String> response = new HashMap<>();
        response.put("username", link.getToGift().getUsername());
        String wish = userToGroupRepository
                .getByGroupAndUser(
                        group,
                        link.getToGift()
                ).getWish();
        response.put("wish", wish);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/group/exit")
    ResponseEntity<Response> exitGroup(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup link = userToGroupRepository.getByGroupAndUser(group, user);
        if (link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (link.getRole() == GroupRole.ADMIN && countAdmins(group) < 2) {
            return new ResponseEntity<>(Response.NOT_ENOUGH_ADMINS, HttpStatus.EXPECTATION_FAILED);
        }

        userToGroupRepository.delete(link);
        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }

    @PostMapping("/group/remove")
    ResponseEntity<Response> removeGroup(@RequestBody Request req) {
        if (req.getExecutor() == null || req.getGroupname() == null) {
            return new ResponseEntity<>(Response.BAD_ARGUMENTS, HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.getByUsername(req.getExecutor());
        if (user == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_USER, HttpStatus.EXPECTATION_FAILED);
        }
        Group group = groupRepository.getByGroupname(req.getGroupname());
        if (group == null) {
            return new ResponseEntity<>(Response.NON_EXISTENT_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (group.getClosed()) {
            return new ResponseEntity<>(Response.GROUP_ALREADY_CLOSED, HttpStatus.EXPECTATION_FAILED);
        }
        UserToGroup link = userToGroupRepository.getByGroupAndUser(group, user);
        if (link == null) {
            return new ResponseEntity<>(Response.NOT_IN_GROUP, HttpStatus.EXPECTATION_FAILED);
        }
        if (link.getRole() != GroupRole.ADMIN) {
            return new ResponseEntity<>(Response.NO_RIGHTS, HttpStatus.EXPECTATION_FAILED);
        }

        groupRepository.delete(group);
        return new ResponseEntity<>(Response.OK, HttpStatus.OK);
    }
}
