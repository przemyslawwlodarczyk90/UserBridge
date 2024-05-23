package com.example.userbridge.infrastructure.repository;

import com.example.userbridge.domain.user.entity.ConfirmationToken;
import com.example.userbridge.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ConfirmationTokenRepositoryTest {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByToken() {
        User user = User.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .email("jan.kowalski@example.com")
                .password("password")
                .enabled(true)
                .build();

        user = userRepository.save(user);

        ConfirmationToken token = ConfirmationToken.builder()
                .token("test-token")
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusHours(24))
                .user(user)
                .build();

        confirmationTokenRepository.save(token);

        Optional<ConfirmationToken> foundToken = confirmationTokenRepository.findByToken("test-token");

        assertThat(foundToken).isPresent();
        assertThat(foundToken.get().getToken()).isEqualTo("test-token");
        assertThat(foundToken.get().getUser().getEmail()).isEqualTo("jan.kowalski@example.com");
    }

    @Test
    public void testFindByTokenNotFound() {
        Optional<ConfirmationToken> foundToken = confirmationTokenRepository.findByToken("non-existent-token");

        assertThat(foundToken).isNotPresent();
    }
}
