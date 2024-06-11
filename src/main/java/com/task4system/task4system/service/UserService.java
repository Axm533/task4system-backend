package com.task4system.task4system.service;

import com.task4system.task4system.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    public Page<User> getUsers(String search, int page, int size, String sortField, String sortDirection);

    void saveAll(List<User> users);

    void saveUser(String name, String surname, String login);

    void updateUser(Long id, String name, String surname, String login);

    public void deleteAllUsers();

    void deleteUser(Long id);
}
