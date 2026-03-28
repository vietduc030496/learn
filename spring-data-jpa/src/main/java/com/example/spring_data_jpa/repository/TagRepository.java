package com.example.spring_data_jpa.repository;

import com.example.spring_data_jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
