package com.task4system.task4system;

import com.task4system.task4system.model.User;
import com.task4system.task4system.repository.UserRepository;
import com.task4system.task4system.service.UserServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImplementation userService;

    private User user1, user2, user3;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new User("Chester", "Raccoon", "raccoonchester");
        user1.setId(1L);
        user2 = new User("Ewa", "Werner", "ewawerner");
        user2.setId(2L);
        user3 = new User("Adam", "Rozdrazewski", "adamrozdrazewski");
        user3.setId(3L);
    }

    @Test
    void getUserById() {
        when(userRepository.getUserById(1L)).thenReturn(user1);

        User foundUser = userService.getUserById(1L);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Chester");
        verify(userRepository, times(1)).getUserById(1L);
    }

    @Test
    void getUsers_withSearch() {
        Page<User> page = new PageImpl<>(Arrays.asList(user1, user2));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        when(userRepository.findByNameContainingOrSurnameContainingOrLoginContaining(anyString(), anyString(), anyString(), eq(pageable))).thenReturn(page);

        Page<User> users = userService.getUsers("search", 0, 10, "name", "asc");

        assertThat(users.getTotalElements()).isEqualTo(2);
        assertThat(users.getContent()).contains(user1, user2);
        verify(userRepository, times(1)).findByNameContainingOrSurnameContainingOrLoginContaining(anyString(), anyString(), anyString(), eq(pageable));
    }

    @Test
    void getUsers_withoutSearch() {
        Page<User> page = new PageImpl<>(Arrays.asList(user1, user2, user3));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        when(userRepository.findAll(pageable)).thenReturn(page);

        Page<User> users = userService.getUsers(null, 0, 10, "name", "asc");

        assertThat(users.getTotalElements()).isEqualTo(3);
        assertThat(users.getContent()).contains(user1, user2, user3);
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void saveAll() {
        List<User> users = Arrays.asList(user1, user2, user3);

        userService.saveAll(users);

        verify(userRepository, times(1)).saveAll(users);
    }

    @Test
    void saveUser() {
        userService.saveUser("New", "User", "newuser");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertThat(savedUser.getName()).isEqualTo("New");
        assertThat(savedUser.getSurname()).isEqualTo("User");
        assertThat(savedUser.getLogin()).isEqualTo("newuser");
    }

    @Test
    void updateUser_existing() {
        when(userRepository.getUserById(1L)).thenReturn(user1);

        userService.updateUser(1L, "Updated", "Raccoon", "updatedlogin");

        verify(userRepository, times(1)).getUserById(1L);
        verify(userRepository, times(1)).save(user1);
        assertThat(user1.getName()).isEqualTo("Updated");
        assertThat(user1.getSurname()).isEqualTo("Raccoon");
        assertThat(user1.getLogin()).isEqualTo("updatedlogin");
    }

    @Test
    void updateUser_nonExisting() {
        when(userRepository.getUserById(4L)).thenReturn(null);

        userService.updateUser(4L, "Non", "Existing", "nonexisting");

        verify(userRepository, times(1)).getUserById(4L);
        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void deleteAllUsers() {
        userService.deleteAllUsers();

        verify(userRepository, times(1)).deleteAll();
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }
}

