package userbridge.domain.user.service;


import userbridge.domain.user.entity.User;
import userbridge.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditUserService {
    private final UserRepository userRepository;

    @Autowired
    public EditUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void edit(userbridge.domain.user.dto.UserDto userDto) {
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