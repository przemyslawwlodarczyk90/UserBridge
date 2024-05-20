package userbridge.domain.user.DTO;

import lombok.Data;

@Data
public class LoginDto {
    private String email;
    private String password;
}