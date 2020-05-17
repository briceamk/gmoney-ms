package cm.g2s.uaa.service;

import cm.g2s.uaa.shared.payload.SignUp;
import cm.g2s.uaa.shared.dto.UserDto;

public interface AuthService {
    UserDto signUp(SignUp signUp);
}
