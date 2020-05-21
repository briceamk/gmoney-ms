package cm.g2s.uaa.service;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.service.broker.consumer.payload.CreateAccountResponse;
import cm.g2s.uaa.service.broker.consumer.payload.CreatePartnerResponse;
import cm.g2s.uaa.shared.dto.UserDto;


public interface UserManagerService {
    UserDto createNewUser(User user);

    void processPartnerCreationResponse(String userId,
                                        Boolean creationPartnerError,
                                        CreatePartnerResponse response);

    void processAccountCreationResponse(String userId, Boolean creationAccountError, CreateAccountResponse response);
}
