package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.web.payload.SignUp;

public interface AuthService {
    User signUp(SignUp signUp);
}
