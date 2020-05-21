package cm.g2s.uaa.service.broker.consumer.impl;

import cm.g2s.uaa.service.UserManagerService;
import cm.g2s.uaa.service.broker.consumer.UserEventConsumerService;
import cm.g2s.uaa.service.broker.consumer.payload.CreateAccountResponse;
import cm.g2s.uaa.service.broker.consumer.payload.CreatePartnerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("userEventConsumerService")
public class UserEventConsumerServiceImpl implements UserEventConsumerService {

    final UserManagerService userManagerService;

    @Override
    @StreamListener(target = "partnerCreatedResponseChannel")
    public void observeCreatePartnerResponse(CreatePartnerResponse response) {
        log.info("Receiving Partner creation response from partner-service");
        userManagerService.processPartnerCreationResponse(response.getUserDto().getId(), response.getCreationPartnerError(), response);
    }

    @Override
    @StreamListener(target = "accountCreatedResponseChannel")
    public void observeCreateAccountResponse(CreateAccountResponse response) {
        log.info("Receiving Account creation response from account-service");
        userManagerService.processAccountCreationResponse(response.getUserDto().getId(), response.getCreationAccountError(), response);
    }
}
