package cm.g2s.account.service.broker.service.consumer.impl;

import cm.g2s.account.service.AccountService;
import cm.g2s.account.service.broker.payload.CreateAccountResponse;
import cm.g2s.account.service.broker.service.consumer.AccountEventConsumerService;
import cm.g2s.account.service.broker.service.publisher.AccountEventPublisherService;
import cm.g2s.account.service.partner.model.PartnerDto;
import cm.g2s.account.service.user.model.UserDto;
import cm.g2s.account.shared.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("accountEventConsumerService")
public class AccountEventConsumerServiceImpl implements AccountEventConsumerService {

    private final AccountService accountService;
    private final AccountEventPublisherService publisherService;

    @Override
    @StreamListener(target = "accountCreatedChannel")
    public void observeAccountCreateRequest(UserDto userDto) {

        CreateAccountResponse.CreateAccountResponseBuilder builder = CreateAccountResponse.builder();
        try{
            log.info("Receiving Create Account Request from uaa-service");
            AccountDto accountDto = userDtoToAccountDto(userDto);
            accountDto = accountService.create(null, accountDto);
            builder.userDto(userDto)
                    .creationAccountError(false);
            log.info("Creation Account Request Successfully");
        } catch (Exception e) {
            log.error("Error when creating account with data from uaa-service");
            builder.userDto(userDto)
                    .creationAccountError(true);
        } finally {
            //Sending Response to uaa-service
            publisherService.onCreateAccountResponseEvent(builder.build());
        }

    }

    private AccountDto userDtoToAccountDto(UserDto userDto) {
        return AccountDto.builder()
                .partnerDto(PartnerDto.builder().id(userDto.getPartnerId()).build())
                .userDto(userDto)
                .build();
    }
}
