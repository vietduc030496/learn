package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.entity.User;
import com.example.spring_data_jpa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public List<User> getAllUsersFixNPlusOneWithJoinFetch() {
        return userRepo.findAllWithDepartment();
    }

    public List<User> getAllUsersFixNPlusOneWithEntityGraph() {
        return userRepo.findAll();
    }

    public User getUserByIdAndUpdate(Long id) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        User user = userRepo.findById(id).orElse(null);
        if (user != null) {
            this.updateUser(user);
            return user;
        }

        return null;
    }

    @Transactional
    public void updateUser(User user) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        user.setUsername("new username transactional");
        userRepo.save(user);
        throw new RuntimeException("Error");
    }

}
