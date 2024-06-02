package com.task4system.task4system.controller;

import com.task4system.task4system.model.User;
import com.task4system.task4system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){ return userService.getAllUsers();}

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){ return userService.getUserById(id);}

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserByName(@RequestParam String name){
        List<User> users = userService.findAllByName((name));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserBySurname(@RequestParam String surname){
        List<User> users = userService.findAllByName((surname));
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserByLogin(@RequestParam String login){
        List<User> users = userService.findAllByName((login));
        return ResponseEntity.ok(users);
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String login
    ){
        userService.saveUser(name, surname, login);
        return ResponseEntity.ok("User saved!");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String login
    ){
        userService.updateUser(id, name, surname, login);
        return ResponseEntity.ok("User updated!");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam int id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
}
