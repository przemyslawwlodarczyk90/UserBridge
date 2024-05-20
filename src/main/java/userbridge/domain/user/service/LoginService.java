package userbridge.domain.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userbridge.domain.user.dto.LoginDto;
import userbridge.domain.user.entity.User;
import userbridge.infrastructure.repository.UserRepository;

@Service
public class LoginService {
    private final UserRepository userRepository;

    @Autowired
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (user.getPassword().equals(loginDto.getPassword())) {
            return "JWT_TOKEN";
        }
        throw new RuntimeException("Invalid email or password");
    }
}
