package com.task4system.task4system.service;

import com.task4system.task4system.model.User;

import java.util.List;

public interface UserService {

    User getUserById(int id);

    List<User> getAllUsers();

    List<User> findAllByName(String name);

    List<User> findAllBySurname(String surname);

    List<User> findAllByLogin(String login);

    List<User> generateSampleUserData();

    void saveUser(String name, String surname, String login);

    void updateUser(int id, String name, String surname, String login);

    void deleteUser(int id);
}
