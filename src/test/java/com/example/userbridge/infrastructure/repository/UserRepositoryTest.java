package com.example.userbridge.infrastructure.repository;

import com.example.userbridge.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
 class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
     void testFindByEmail() {
        User user = User.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .password("password")
                .enabled(true)
                .build();

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("jan.kowalski@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("jan.kowalski@example.com");
        assertThat(foundUser.get().getFirstName()).isEqualTo("Jan");
        assertThat(foundUser.get().getLastName()).isEqualTo("Kowalski");
    }

    @Test
     void testFindByEmailNotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        assertThat(foundUser).isNotPresent();
    }
}
