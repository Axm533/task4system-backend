package com.task4system.task4system.controller;

import com.task4system.task4system.model.User;
import com.task4system.task4system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){ return userService.getUserById(id);}

    @PostMapping("/upload")
    public ResponseEntity<?> uploadUsers(@RequestBody List<User> users) {
        try {
            userService.saveAll(users);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving users");
        }
    }

    /*@GetMapping("/search")
    public ResponseEntity<List<User>> searchUser(@RequestParam String searchTerm){
        List<User> users = userService.findAllByNameOrSurnameOrLogin(searchTerm);
        return ResponseEntity.ok(users);
    }*/

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
            @RequestParam Long id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam String login
    ){
        userService.updateUser(id, name, surname, login);
        return ResponseEntity.ok("User updated!");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
}
