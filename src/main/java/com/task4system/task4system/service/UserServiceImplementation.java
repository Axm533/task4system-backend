package com.task4system.task4system.service;

import com.task4system.task4system.model.User;
import com.task4system.task4system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(int id){
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public List<User> findAllByName(String name){
        return userRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<User> findAllBySurname(String surname){
        return userRepository.findAllBySurnameContainingIgnoreCase(surname);
    }

    @Override
    public List<User> findAllByLogin(String login){
        return userRepository.findAllByLoginContainingIgnoreCase(login);
    }

    public List<User> generateSampleUserData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("name" + (i + 1), "surname" + (i + 1), "login" + (i + 1)));
        }
        return users;
    }


    @Override
    public void saveUser(String name, String surname, String login){
        userRepository.save(new User(name, surname, login));
    }

    @Override
    public void updateUser(int id, String name, String surname, String login){
        User user = userRepository.getUserById(id);
        if(user != null){
            user.setName(name);
            user.setSurname(surname);
            user.setLogin(login);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

}
