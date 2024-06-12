package com.task4system.task4system;

import com.task4system.task4system.controller.UserController;
import com.task4system.task4system.model.User;
import com.task4system.task4system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsers() throws Exception {
        User user1 = new User("Chester", "Raccoon", "raccoonchester");
        User user2 = new User("Ewa", "Werner", "ewawerner");
        List<User> users = Arrays.asList(user1, user2);
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<User> page = new PageImpl<>(users, pageable, users.size());

        when(userService.getUsers(anyString(), anyInt(), anyInt(), anyString(), anyString())).thenReturn(page);

        mockMvc.perform(get("/api/users")
                        .param("search", "")
                        .param("page", "0")
                        .param("size", "50")
                        .param("sortField", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Pages", "1"))
                .andExpect(jsonPath("$.content.length()").value(users.size()))
                .andExpect(jsonPath("$.content[0].name").value("Chester"))
                .andExpect(jsonPath("$.content[1].name").value("Ewa"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User("Chester", "Raccoon", "raccoonchester");
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Chester"))
                .andExpect(jsonPath("$.surname").value("Raccoon"))
                .andExpect(jsonPath("$.login").value("raccoonchester"));
    }

    @Test
    public void testUploadUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("Chester", "Raccoon", "raccoonchester"),
                new User("Ewa", "Werner", "ewawerner")
        );

        mockMvc.perform(post("/api/users/upload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"name\":\"Chester\",\"surname\":\"Raccoon\",\"login\":\"raccoonchester\"},{\"name\":\"Ewa\",\"surname\":\"Werner\",\"login\":\"ewawerner\"}]"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveUser() throws Exception {
        mockMvc.perform(post("/api/users/save")
                        .param("name", "Chester")
                        .param("surname", "Raccoon")
                        .param("login", "raccoonchester"))
                .andExpect(status().isOk())
                .andExpect(content().string("User saved!"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        mockMvc.perform(post("/api/users/update")
                        .param("id", "1")
                        .param("name", "Chester")
                        .param("surname", "Raccoon")
                        .param("login", "raccoonchester"))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated!"));
    }

    @Test
    public void testDeleteAllUsers() throws Exception {
        mockMvc.perform(delete("/api/users/deleteAll"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/delete")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted!"));
    }
}
