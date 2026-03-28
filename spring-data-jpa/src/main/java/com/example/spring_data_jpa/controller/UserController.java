package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.entity.User;
import com.example.spring_data_jpa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserByIdAndUpdate(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        for (User user : allUsers) {
            System.out.println(user.getUsername() + " - " + user.getDepartment().getDepartmentName());
        }

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/join-fetch")
    public ResponseEntity<List<User>> getAllUsersFixNPlusOneWithJoinFetch() {
        List<User> allUsers = userService.getAllUsersFixNPlusOneWithJoinFetch();

        for (User user : allUsers) {
            System.out.println(user.getUsername() + " - " + user.getDepartment().getDepartmentName());
        }

        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/entity-graph")
    public ResponseEntity<List<User>> getAllUsersFixNPlusOneWithEntityGraph() {
        List<User> allUsers = userService.getAllUsersFixNPlusOneWithEntityGraph();

        for (User user : allUsers) {
            System.out.println(user.getUsername() + " - " + user.getDepartment().getDepartmentName());
        }

        return ResponseEntity.ok(allUsers);
    }

}
