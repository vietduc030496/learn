package com.example.spring_data_jpa.repository;

import com.example.spring_data_jpa.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.department")
    List<User> findAllWithDepartment();

    @EntityGraph(attributePaths = {"department"})
    @SuppressWarnings("NullableProblems")
    List<User> findAll();
}
