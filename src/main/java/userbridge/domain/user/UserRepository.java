package userbridge.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}