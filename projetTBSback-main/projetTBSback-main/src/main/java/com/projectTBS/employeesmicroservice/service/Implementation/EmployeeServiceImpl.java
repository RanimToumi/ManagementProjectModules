package com.projectTBS.employeesmicroservice.service.Implementation;


import com.projectTBS.employeesmicroservice.dto.EmployeeDTO;
import com.projectTBS.employeesmicroservice.entity.EmployeeInfo;
import com.projectTBS.employeesmicroservice.mapper.EmployeeMapper;
import com.projectTBS.employeesmicroservice.repo.EmployeeRepository;
import com.projectTBS.employeesmicroservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        EmployeeInfo employee = employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        EmployeeInfo savedEmployee = employeeRepository.save(employee);
        return employeeMapper.mapEmployeeToEmployeeDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<EmployeeInfo> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employeeMapper.mapEmployeeToEmployeeDTO(employee.get());
        } else {
            // Gérer la non-existence de l'employé (par exemple, lever une exception)
            return null;
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeInfo> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeMapper::mapEmployeeToEmployeeDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<EmployeeInfo> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            EmployeeInfo updatedEmployee = employeeMapper.mapEmployeeDTOToEmployee(employeeDTO);
            updatedEmployee.setId(id); // Assurez-vous que l'ID reste le même
            EmployeeInfo savedEmployee = employeeRepository.save(updatedEmployee);
            return employeeMapper.mapEmployeeToEmployeeDTO(savedEmployee);
        } else {
            // Gérer la non-existence de l'employé (par exemple, lever une exception)
            return null;
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
