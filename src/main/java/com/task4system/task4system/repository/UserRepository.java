package com.task4system.task4system.repository;

import com.task4system.task4system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);

    void deleteById(Long id);

    //List<User> findAllByNameOrSurnameOrLoginContainingIgnoreCase(String searchTerm);

}
