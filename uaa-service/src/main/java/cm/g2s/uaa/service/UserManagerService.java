package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.service.broker.payload.CreatePartnerResponse;
import cm.g2s.uaa.web.payload.SignUp;


public interface UserManagerService {
    User createNewUser(User user, SignUp signUp);

    void processPartnerCreationResponse(String userId,
                                        Boolean creationPartnerError,
                                        CreatePartnerResponse response);

    void processAccountCreationResponse(String userId, Boolean creationAccountError);
}
