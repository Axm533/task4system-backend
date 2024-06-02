package com.task4system.task4system.repository;

import com.task4system.task4system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserById(int id);

    List<User> findAllByNameContainingIgnoreCase(String name);

    List<User> findAllBySurnameContainingIgnoreCase(String surname);

    List<User> findAllByLoginContainingIgnoreCase(String login);

}
