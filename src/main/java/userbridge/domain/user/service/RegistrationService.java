package userbridge.domain.user.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userbridge.domain.user.dto.UserDto;
import userbridge.domain.user.entity.User;
import userbridge.domain.user.mapper.UserDtoMapper;
import userbridge.infrastructure.repository.UserRepository;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final UserDtoMapper dtoMapper;

    @Autowired
    public RegistrationService(UserRepository userRepository, UserDtoMapper dtoMapper) {
        this.userRepository = userRepository;
        this.dtoMapper = dtoMapper;
    }

    public void register(UserDto userDto) {
        User user = dtoMapper.toUser(userDto);
        userRepository.save(user);
    }
}
