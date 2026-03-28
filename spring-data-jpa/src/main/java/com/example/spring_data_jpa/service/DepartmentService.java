package com.example.spring_data_jpa.service;

import com.example.spring_data_jpa.entity.Department;
import com.example.spring_data_jpa.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DepartmentService {

    private final DepartmentRepository deptRepo;

    public List<Department> getAllDepartmentsFixNPlusOneWithSubSelect() {
        return deptRepo.findAll();
    }
}
