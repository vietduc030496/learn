package com.example.spring_data.repository;

import com.example.spring_data.entity.user.CCCDEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CCCDRepository extends JpaRepository<CCCDEntity, Long> {
}
