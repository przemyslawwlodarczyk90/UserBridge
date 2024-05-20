package userbridge.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userbridge.domain.user.dto.UserDto;
import userbridge.domain.user.entity.User;
import userbridge.infrastructure.repository.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;

    @Autowired
    public RegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .street(userDto.getStreet())
                .postalCode(userDto.getPostalCode())
                .city(userDto.getCity())
                .build();
        userRepository.save(user);
    }
}
