package com.example.spring_data.controller;

import com.example.spring_data.entity.user.UserEntity;
import com.example.spring_data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserEntity>> getAllUsers(
            @PageableDefault
            @SortDefault(sort = "id") Pageable pageable) {
//        PageRequest pageRequest = PageRequest.of(0, 10);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping
    public ResponseEntity<UserEntity> createNewUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userService.createUser(userEntity));
    }

}
