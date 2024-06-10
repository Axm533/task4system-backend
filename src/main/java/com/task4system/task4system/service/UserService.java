package com.task4system.task4system.service;

import com.task4system.task4system.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    void saveAll(List<User> users);

    //List<User> findAllByNameOrSurnameOrLogin(String searchTerm);

    void saveUser(String name, String surname, String login);

    void updateUser(Long id, String name, String surname, String login);

    void deleteUser(Long id);
}
