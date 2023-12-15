package com.projectTBS.employeesmicroservice.configuration;

import com.projectTBS.employeesmicroservice.entity.EmployeeInfo;
import com.projectTBS.employeesmicroservice.entity.UserInfo;
import com.projectTBS.employeesmicroservice.repo.EmployeeRepository;
import com.projectTBS.employeesmicroservice.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repository;

    @Override  /////pour charger details ta3 user
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<EmployeeInfo> employeeInfo = repository.findByEmail(email);
        return employeeInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + email));

    }
}
