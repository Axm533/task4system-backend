package com.task4system.task4system.repository;

import com.task4system.task4system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User getUserById(Long id);

    void deleteAll();

    void deleteById(Long id);

    Page<User> findByNameContainingOrSurnameContainingOrLoginContaining(String name, String surname, String login, Pageable pageable);

}
