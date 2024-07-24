package com.guava.employee_service.controller;

import com.guava.employee_service.model.Employee;
import com.guava.employee_service.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeRepository repository;

    @PostMapping
    public Employee add(@RequestBody Employee employee){
        LOGGER.info("Employee add: {}", employee);
        return repository.addEmployee(employee);
    }

    @GetMapping
    public List<Employee> findAll(){
        LOGGER.info("Employee find");
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable("id") Long id){
        LOGGER.info("Employee find: id={}",id);
        return repository.findById(id);
    }

    @GetMapping("/department/{departmentId}")
    public List<Employee> findByDepartment(@PathVariable("departmentId") Long departmentId){
        LOGGER.info("Employee find: departnentId={}", departmentId);
        return repository.findByDepartment(departmentId);
    }
}
