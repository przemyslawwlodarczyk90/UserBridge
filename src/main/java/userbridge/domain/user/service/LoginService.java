package userbridge.domain.user.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import userbridge.domain.user.DTO.LoginDto;
import userbridge.infrastructure.repository.UserRepository;


@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if (user.getPassword().equals(loginDto.getPassword())) {
            return jwtTokenProvider.createToken(user.getEmail());
        }
        throw new UnauthorizedException("Invalid email or password");
    }
}