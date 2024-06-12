package com.task4system.task4system;


import com.task4system.task4system.model.User;
import com.task4system.task4system.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user1, user2, user3;

    @BeforeEach
    public void setUp(){
        user1 = new User("Chester", "Raccoon", "raccoonchester");
        user2 = new User("Ewa", "Werner", "ewawerner");
        user3 = new User("Adam", "Rozdrazewski", "adamrozdrazewski");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        userRepository.saveAll(users);
    }

    @AfterEach
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void getUserId(){
        User foundUser = userRepository.getUserById(user1.getId());
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Chester");
    }

    @Test
    public void testDeleteById() {
        userRepository.deleteById(user1.getId());
        Optional<User> deletedUser = userRepository.findById(user1.getId());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void findByNameContainingOrSurnameContainingOrLoginContaining() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> users = userRepository.findByNameContainingOrSurnameContainingOrLoginContaining("Chester", "Raccoon", "raccoonchester", pageable);
        assertThat(users.getTotalElements()).isEqualTo(1);
        assertThat(users.getContent().get(0).getName()).isEqualTo("Chester");

        users = userRepository.findByNameContainingOrSurnameContainingOrLoginContaining("Werner", "Werner", "ewawerner", pageable);
        assertThat(users.getTotalElements()).isEqualTo(1);
        assertThat(users.getContent().get(0).getName()).isEqualTo("Ewa");

        users = userRepository.findByNameContainingOrSurnameContainingOrLoginContaining("Rozdrazewski", "Rozdrazewski", "adamrozdrazewski", pageable);
        assertThat(users.getTotalElements()).isEqualTo(1);
        assertThat(users.getContent().get(0).getName()).isEqualTo("Adam");
    }
}
