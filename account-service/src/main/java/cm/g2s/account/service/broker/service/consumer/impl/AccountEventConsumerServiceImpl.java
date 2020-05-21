package cm.g2s.account.service.broker.service.consumer.impl;

import cm.g2s.account.service.AccountService;
import cm.g2s.account.service.broker.payload.CreateAccountResponse;
import cm.g2s.account.service.broker.payload.DebitAccount;
import cm.g2s.account.service.broker.payload.DebitAccountResponse;
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
        log.info("Receiving Create Account Request from uaa-service");
        CreateAccountResponse.CreateAccountResponseBuilder builder = CreateAccountResponse.builder();
        try{
            AccountDto accountDto = userDtoToAccountDto(userDto);
            accountDto = accountService.create(null, accountDto);
            //We Set PartnerDto in  response
            userDto.setPartnerDto(accountDto.getPartnerDto());
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

    @Override
    @StreamListener(target = "accountDebitedChannel")
    public void observeAccountDebitRequest(DebitAccount debitAccount) {
        log.info("Receiving Debit Account Request from loan-service");
        DebitAccountResponse.DebitAccountResponseBuilder builder = DebitAccountResponse.builder();
        try {
            accountService.debitAccount(debitAccount.getAccountId(), debitAccount.getDebitAmount());
            builder.debitAccountError(false).loanId(debitAccount.getLoanId());
            log.info("Debit Account Request Successfully");
        }catch (Exception e) {
            log.error("Error when debiting account with data from loan-service");
            builder.debitAccountError(true).loanId(debitAccount.getLoanId());
        } finally {
            //Sending Response to uaa-service
            publisherService.onDebitAccountResponseEvent(builder.build());
        }
    }

    private AccountDto userDtoToAccountDto(UserDto userDto) {
        return AccountDto.builder()
                .partnerDto(userDto.getPartnerDto())
                .userDto(userDto)
                .build();
    }
}
