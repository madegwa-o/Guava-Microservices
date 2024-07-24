package com.guava.department_service.controller;

import com.guava.department_service.client.EmployeeClient;
import com.guava.department_service.model.Department;
import com.guava.department_service.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    private static final Logger LOGGER
            = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeClient employeeClient;

    @PostMapping
    public Department add(@RequestBody Department department){
        LOGGER.info("Department add: {}",department);
        return departmentRepository.addDepartment(department);
    }

    @GetMapping
    public List<Department> fingAll(){
        LOGGER.info("Department find");
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id){
        LOGGER.info("Department find: id={}",id);
        return departmentRepository.findById(id);
    }

    @GetMapping("/with-employees")
    public List<Department> fingAllWithEmployees(){
        LOGGER.info("Department find");
        List<Department> departments =
                departmentRepository.findAll();
        departments.forEach(department ->
                department.setEmployees(
                        employeeClient.findByDepartment(department.getId())
                ));
        return departments;
    }
}
