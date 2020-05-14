package cm.g2s.uaaservice.service;

import cm.g2s.uaaservice.shared.dto.SignUpDto;
import cm.g2s.uaaservice.shared.dto.UserDto;

public interface AuthService {
    UserDto signUp(SignUpDto signUp);
}
