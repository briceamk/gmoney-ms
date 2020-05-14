package cm.g2s.uaaservice.repository;

import cm.g2s.uaaservice.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

    Optional<User> findByUsernameOrEmailOrMobile(String username, String email, String mobile);

}
