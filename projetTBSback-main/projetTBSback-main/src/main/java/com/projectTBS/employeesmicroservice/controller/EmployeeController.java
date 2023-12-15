package com.projectTBS.employeesmicroservice.controller;


import com.projectTBS.employeesmicroservice.dto.EmployeeDTO;
import com.projectTBS.employeesmicroservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EmployeeDTO createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_INGENIEUR')")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_INGENIEUR','ROLE_ADMIN')")
    public List<EmployeeDTO> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.updateEmployee(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
