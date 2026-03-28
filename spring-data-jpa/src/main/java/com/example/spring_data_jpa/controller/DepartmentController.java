package com.example.spring_data_jpa.controller;

import com.example.spring_data_jpa.entity.Department;
import com.example.spring_data_jpa.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/subselect")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartmentsFixNPlusOneWithSubSelect();

        for (Department department : departments) {
            System.out.println(department.getDepartmentName() + " - " + department.getUsers().size());
        }

        return ResponseEntity.ok(departments);
    }
}
