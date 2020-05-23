package cm.g2s.loan.service.broker.consumer.impl;

import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.service.broker.consumer.LoanEventConsumerService;
import cm.g2s.loan.service.broker.payload.CreateTransactionResponse;
import cm.g2s.loan.service.broker.payload.DebitAccountResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("loanEventConsumerService")
public class LoanEventConsumerServiceImpl implements LoanEventConsumerService {

    private final LoanManagerService loanManagerService;

    @Override
    @StreamListener(target = "accountDebitedResponseChannel")
    public void observeDebitAccountResponse(DebitAccountResponse response) {
        log.info("Receiving Debit Account response from account-service");
        loanManagerService.processDebitAccountResponse(null, response.getLoanId(), response.getDebitAccountError());
    }

    @Override
    @StreamListener(target = "transactionCreatedResponseChannel")
    public void observeTransactionCreateResponse(CreateTransactionResponse response) {
        log.info("Receiving Create Transaction response from transaction-service");
        loanManagerService.processCreateTransactionResponse(null, response.getLoanId(), response.getCreateTransactionError());
    }

}
