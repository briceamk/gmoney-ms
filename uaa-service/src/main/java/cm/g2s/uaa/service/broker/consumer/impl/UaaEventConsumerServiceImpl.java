package cm.g2s.uaa.service.broker.consumer.impl;

import cm.g2s.uaa.service.UserManagerService;
import cm.g2s.uaa.service.broker.consumer.UaaEventConsumerService;
import cm.g2s.uaa.service.broker.payload.CreateAccountResponse;
import cm.g2s.uaa.service.broker.payload.CreatePartnerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("userEventConsumerService")
public class UaaEventConsumerServiceImpl implements UaaEventConsumerService {

    final UserManagerService userManagerService;

    @Override
    @StreamListener(target = "partnerChannel", condition = "headers['partner'] == 'createPartnerResponse'")
    public void observeCreatePartnerResponse(@Payload  CreatePartnerResponse createPartnerResponse) {
        log.info("Receiving Partner creation response from partner-service");
        userManagerService.processPartnerCreationResponse(createPartnerResponse.getUserId(),
                createPartnerResponse.getCreationPartnerError(), createPartnerResponse);
    }

    @Override
    @StreamListener(target = "accountChannel", condition = "headers['account'] == 'createAccountResponse'")
    public void observeCreateAccountResponse(@Payload CreateAccountResponse createAccountResponse) {
        log.info("Receiving Account creation response from account-service");
        userManagerService.processAccountCreationResponse(createAccountResponse.getUserId(),
                createAccountResponse.getCreationAccountError());
    }
}
