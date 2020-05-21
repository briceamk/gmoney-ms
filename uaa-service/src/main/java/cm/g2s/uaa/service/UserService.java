package cm.g2s.uaa.service;

import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.dto.UserDtoPage;
import cm.g2s.uaa.shared.payload.ResetPassword;
import org.springframework.data.domain.PageRequest;

public interface UserService {
    UserDto create( UserDto userDto);

    void update( UserDto userDto);

    void resetPassword( String userId, ResetPassword resetPassword);

    UserDtoPage findAll(String fullName, String username, String email, String mobile, PageRequest pageRequest);

    UserDto findById( String id);

    void deleteById( String id);
}
