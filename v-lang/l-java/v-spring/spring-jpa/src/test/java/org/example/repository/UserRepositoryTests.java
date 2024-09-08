package org.example.repository;

import org.example.model.User;
import org.example.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void create() {
        userRepository.save(new User("admin", "admin", UserRole.User));
    }

    @Test
    void fillAll() {
        userRepository.findAll().forEach(System.out::println);
    }

    @Test
    void fillById() {
        userRepository.findById(1L).ifPresent(System.out::println);
    }

    @Test
    void findByDeletedAt() {
        userRepository.findByDeletedAt().forEach(System.out::println);
    }

    @Test
    void deleteById() {
        userRepository.deleteById(1L);
    }
}
