package com.example.spring_data.service;

import com.example.spring_data.entity.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserEntity createUser(UserEntity userEntity);

    Page<UserEntity> getAllUsers(Pageable pageable);
}
