package com.ordering.procurementFlow.repositories;

import com.ordering.procurementFlow.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserById(Long id);
    void deleteUserById(Long id);
    Optional<User> findByEmail(String email);
}
