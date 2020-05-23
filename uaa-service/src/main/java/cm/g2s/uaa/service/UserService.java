package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.web.payload.ResetPassword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    User create(User user);

    void update( User user);

    void resetPassword( String userId, ResetPassword resetPassword);

    Page<User> findAll(String firstName, String lastName, String username,
                       String email, String mobile, PageRequest pageRequest);

    User findById( String id);

    void deleteById( String id);
}
