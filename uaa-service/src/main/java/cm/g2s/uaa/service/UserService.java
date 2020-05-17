package cm.g2s.uaa.service;

import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.dto.UserDtoPage;
import cm.g2s.uaa.shared.payload.ResetPassword;
import org.springframework.data.domain.PageRequest;

import java.nio.file.attribute.UserPrincipal;

public interface UserService {
    UserDto create(UserPrincipal userPrincipal, UserDto userDto);

    void update(UserPrincipal userPrincipal, UserDto userDto);

    void resetPassword(UserPrincipal userPrincipal, String userId, ResetPassword resetPassword);

    UserDtoPage findAll(UserPrincipal userPrincipal, String fullName, String username, String email, String mobile, PageRequest pageRequest);

    UserDto findById(UserPrincipal userPrincipal, String id);

    void deleteById(UserPrincipal userPrincipal, String id);
}
