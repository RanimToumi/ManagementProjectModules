package com.projectTBS.employeesmicroservice.mapper;


import com.projectTBS.employeesmicroservice.dto.EmployeeDTO;
import com.projectTBS.employeesmicroservice.entity.EmployeeInfo;

import org.springframework.stereotype.Component;


@Component
public interface EmployeeMapper {
    EmployeeDTO mapEmployeeToEmployeeDTO(EmployeeInfo employee);
    EmployeeInfo mapEmployeeDTOToEmployee(EmployeeDTO dto);
}

