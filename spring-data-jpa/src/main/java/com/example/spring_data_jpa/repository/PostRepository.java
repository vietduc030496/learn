package com.example.spring_data_jpa.repository;

import com.example.spring_data_jpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
