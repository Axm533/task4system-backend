package com.task4system.task4system.service;

import com.task4system.task4system.model.User;
import com.task4system.task4system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public Page<User> getUsers(String search, int page, int size, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (search == null || search.isEmpty()) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findByNameContainingOrSurnameContainingOrLoginContaining(search, search, search, pageable);
        }
    }

    @Override
    public void saveAll(List<User> users){
        userRepository.saveAll(users);
    }

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
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
