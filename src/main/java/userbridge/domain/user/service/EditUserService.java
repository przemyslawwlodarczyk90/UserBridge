package userbridge.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userbridge.domain.user.dto.UserDto;
import userbridge.domain.user.entity.User;
import userbridge.domain.user.mapper.UserDtoMapper;
import userbridge.infrastructure.repository.UserRepository;

@Service
public class EditUserService {
    private final UserRepository userRepository;
    private final UserDtoMapper dtoMapper;

    @Autowired
    public EditUserService(UserRepository userRepository, UserDtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
    }

    public void edit(UserDto userDto) {
        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setStreet(userDto.getStreet());
        user.setPostalCode(userDto.getPostalCode());
        user.setCity(userDto.getCity());
        userRepository.save(user);
    }
}
