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
    public User getUserById(Long id){
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public void saveAll(List<User> users){
        userRepository.saveAll(users);
    }

    /*@Override
    public List<User> findAllByNameOrSurnameOrLogin(String searchTerm){
        return userRepository.findAllByNameOrSurnameOrLoginContainingIgnoreCase(searchTerm);
    }*/

    @Override
    public void saveUser(String name, String surname, String login){
        userRepository.save(new User(name, surname, login));
    }

    @Override
    public void updateUser(Long id, String name, String surname, String login){
        User user = userRepository.getUserById(id);
        if(user != null){
            user.setName(name);
            user.setSurname(surname);
            user.setLogin(login);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
