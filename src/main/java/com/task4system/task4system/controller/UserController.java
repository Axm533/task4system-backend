package com.task4system.task4system.controller;

import com.task4system.task4system.model.User;
import com.task4system.task4system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
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

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        try {
            Page<User> usersPage = userService.getUsers(search, page, size, sortField, sortDirection);

            long totalPages = usersPage.getTotalPages();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Pages", String.valueOf(totalPages));

            return new ResponseEntity<>(usersPage, headers, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted!");
    }
}
