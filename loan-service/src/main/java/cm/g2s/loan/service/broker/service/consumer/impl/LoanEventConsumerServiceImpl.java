package cm.g2s.loan.service.broker.service.consumer.impl;

import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.service.broker.service.consumer.LoanEventConsumerService;
import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.loan.service.broker.payload.CreateTransactionResponse;
import cm.g2s.loan.service.broker.payload.DebitAccountResponse;
import cm.g2s.loan.service.broker.payload.SendMoneyResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("loanEventConsumerService")
public class LoanEventConsumerServiceImpl implements LoanEventConsumerService {

    private final LoanManagerService loanManagerService;

    @Override
    @StreamListener(target = "accountChannel", condition = "headers['account']=='debitAccountResponse'")
    public void observeDebitAccountResponse(@Payload  DebitAccountResponse debitAccountResponse) {
        log.info("Receiving Debit Account response from account-service");
        loanManagerService.processDebitAccountResponse(null, debitAccountResponse.getLoanId(),
                debitAccountResponse.getDebitAccountError());
    }

    @Override
    @StreamListener(target = "transactionChannel", condition = "headers['transaction']=='createTransactionResponse'")
    public void observeTransactionCreateResponse(@Payload  CreateTransactionResponse createTransactionResponse) {
        log.info("Receiving Create Transaction response from transaction-service");
        loanManagerService.processCreateTransactionResponse(null, createTransactionResponse.getLoanId(),
                createTransactionResponse.getCreateTransactionError());
    }

    @Override
    @StreamListener(target = "transactionChannel", condition = "headers['transaction']=='sendMoneyResponse'")
    public void observeSendMoneyResponse(@Payload  SendMoneyResponse sendMoneyResponse) {
        log.info("Receiving Send Money response from transaction-service");
        loanManagerService.processSendMoneyResponse(null, sendMoneyResponse.getLoanId(), sendMoneyResponse);

    }

    @Override
    @StreamListener(target = "accountChannel", condition = "headers['account']=='confirmDebitAccountResponse'")
    public void observeConfirmAccountDebitResponse(@Payload  ConfirmDebitAccountResponse confirmDebitAccountResponse) {
        log.info("Receiving Confirm Debit Account response from account-service");
        loanManagerService.processConfirmAccountDebitResponse(null, confirmDebitAccountResponse.getLoanId(),
                confirmDebitAccountResponse);
    }

}
