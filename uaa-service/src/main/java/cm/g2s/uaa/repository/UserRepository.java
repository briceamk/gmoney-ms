package cm.g2s.uaa.repository;

import cm.g2s.uaa.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByMobile(String mobile);

    Optional<User> findByUsernameOrEmailOrMobile(String username, String email, String mobile);

    Page<User> findByMobile(String mobile, PageRequest pageRequest);

    Page<User> findByEmail(String email, PageRequest pageRequest);

    Page<User> findByUsername(String username, PageRequest pageRequest);

    Page<User> findByFullName(String fullName, PageRequest pageRequest);
}
