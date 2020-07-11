package cm.g2s.account.service.broker.service.consumer.impl;


import cm.g2s.account.domain.model.Account;
import cm.g2s.account.domain.model.AccountState;
import cm.g2s.account.service.AccountService;
import cm.g2s.account.service.broker.payload.*;
import cm.g2s.account.service.broker.service.consumer.AccountEventConsumerService;
import cm.g2s.account.service.broker.service.publisher.AccountEventPublisherService;
import cm.g2s.account.service.partner.model.PartnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("accountEventConsumerService")
public class AccountEventConsumerServiceImpl implements AccountEventConsumerService {

    private final AccountService accountService;
    private final AccountEventPublisherService publisherService;

    @Override
    //@StreamListener(target = "uaaChannel", condition = "headers['uaa'] == 'createAccount'")
    @StreamListener(target = "uaaCreateAccountChannel", condition = "headers['uaa'] == 'createAccount'")
    public void observeAccountCreateRequest(@Payload CreateAccountRequest createAccountRequest) {
        log.info("Receiving Create Account Request from uaa-service");
        CreateAccountResponse.CreateAccountResponseBuilder builder = CreateAccountResponse.builder();
        try{
            Account account = transform(createAccountRequest);
            PartnerDto partnerDto = PartnerDto.builder()
                    .id(createAccountRequest.getPartnerId())
                    .build();
            account = accountService.create(null, account, partnerDto);
            //We Set PartnerDto in  response
            builder.userId(account.getUserId())
                    .creationAccountError(false);
            log.info("Creation Account Request Successfully");
        } catch (Exception e) {
            log.error("Error when creating account with data from uaa-service");
            builder.userId(createAccountRequest.getUserId())
                    .creationAccountError(true);
        } finally {
            //Sending Response to uaa-service
            publisherService.onCreateAccountResponseEvent(builder.build());
        }

    }

    @Override
    //@StreamListener(target = "loanChannel", condition = "headers['loan'] == 'debitAccount'")
    @StreamListener(target = "loanDebitAccountChannel", condition = "headers['loan'] == 'debitAccount'")
    public void observeAccountDebitRequest(@Payload DebitAccountRequest debitAccountRequest) {

        log.info("Receiving Debit Account Request from loan-service");
        DebitAccountResponse.DebitAccountResponseBuilder builder = DebitAccountResponse.builder();
        try {
            accountService.debitAccount(null, debitAccountRequest.getAccountId(), debitAccountRequest.getDebitAmount());
            builder.debitAccountError(false).loanId(debitAccountRequest.getLoanId());
            log.info("Debit Account Request Successfully");
        }catch (Exception e) {
            log.error("Error when debiting account with data from loan-service");
            builder.debitAccountError(true).loanId(debitAccountRequest.getLoanId());
        } finally {
            //Sending Response to loan-service
            publisherService.onDebitAccountResponseEvent(builder.build());
        }
    }

    @Override
    //@StreamListener(target = "loanChannel", condition = "headers['loan'] == 'confirmDebitAccount'")
    @StreamListener(target = "loanConfirmDebitAccountChannel", condition = "headers['loan'] == 'confirmDebitAccount'")
    public void observeConfirmAccountDebitRequest(@Payload ConfirmDebitAccountRequest confirmDebitAccountRequest) {
        log.info("Receiving Confirm Debit Account Request from loan-service");
        ConfirmDebitAccountResponse.ConfirmDebitAccountResponseBuilder builder = ConfirmDebitAccountResponse.builder();
        try {
            accountService.confirmDeBitAccount(null, confirmDebitAccountRequest.getAccountId(),
                    AccountState.valueOf(confirmDebitAccountRequest.getNextAccountState()));
            builder.confirmDebitAccountError(false).loanId(confirmDebitAccountRequest.getLoanId());
            log.info("Confirm Debit Account Request Successfully");
        }catch (Exception e) {
            log.error("Error when confirming debiting account with data from loan-service");
            builder.confirmDebitAccountError(true).loanId(confirmDebitAccountRequest.getLoanId());
        } finally {
            //Sending Response to loan-service
            publisherService.onConfirmDebitAccountResponseEvent(builder.build());
        }
    }

    private Account transform(CreateAccountRequest accountRequest) {
        return Account.builder()
                .partnerId(accountRequest.getPartnerId())
                .userId(accountRequest.getUserId())
                .build();
    }


}
