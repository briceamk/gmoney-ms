package cm.g2s.transaction.service.broker.consumer.impl;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.domain.model.TransactionMode;
import cm.g2s.transaction.domain.model.TransactionState;
import cm.g2s.transaction.service.TransactionService;
import cm.g2s.transaction.service.broker.consumer.TransactionEventConsumerService;
import cm.g2s.transaction.service.broker.payload.*;
import cm.g2s.transaction.service.broker.publisher.TransactionEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service("transactionEventConsumerService")
public class TransactionEventConsumerServiceImpl implements TransactionEventConsumerService {
    private final TransactionService transactionService;
    private final TransactionEventPublisherService publisherService;

    @Override
    @StreamListener("transactionCreatedChannel")
    public void observeCreateTransactionRequest(@Payload  CreateTransactionRequest transactionRequest) {
        CreateTransactionResponse.CreateTransactionResponseBuilder builder = CreateTransactionResponse.builder();
        log.info("Receiving Create Transaction Request from loan-service");
        try {
            Transaction transaction = transform(transactionRequest);
            transactionService.create(null, transaction);
            builder.loanId(transaction.getLoanId())
                    .createTransactionError(false);
            log.info(" Create Transaction Request Successfully");
        }catch (Exception e) {
            log.error("Error when creating transaction with data from loan-service");
            builder.loanId(transactionRequest.getLoanId())
                    .createTransactionError(true);
        }finally {
            //Sending Response to uaa-service
            publisherService.onCreateTransactionResponseEvent(builder.build());
        }
    }

    @Override
    @StreamListener("sendMoneyChannel")
    public void observeSendMoneyRequest(@Payload  SendMoneyRequest sendMoneyRequest) {

        if(sendMoneyRequest.getEventType().equals(JobEventType.SEND_MONEY)) {
            SendMoneyResponse.SendMoneyResponseBuilder builder = SendMoneyResponse.builder();
            transactionService.findReadyToSend(null, TransactionState.TO_SEND).forEach(transaction -> {
                builder.loanId(transaction.getLoanId());
                Boolean result  = transactionService.sendMoney(null, transaction);
                if(result) {
                    builder.sendMoneyError(false);
                } else {
                    builder.sendMoneyError(true)
                            .errorMessage("Failed to send your money! we will retry later");
                }

                publisherService.onSendMoneyResponseEvent(builder.build());
            });

        }

    }

    private Transaction transform(CreateTransactionRequest transactionRequest) {
        return  Transaction.builder()
                .originRef(transactionRequest.getOriginRef())
                .mobile(transactionRequest.getMobile())
                .amount(transactionRequest.getAmount())
                .issueDate(transactionRequest.getIssueDate())
                .mode(TransactionMode.valueOf(transactionRequest.getMode()))
                .loanId(transactionRequest.getLoanId() != null? transactionRequest.getLoanId() : "")
                .partnerId(transactionRequest.getPartnerId())
                .userId(transactionRequest.getUserId())
                .accountId(transactionRequest.getAccountId())
                .companyId(transactionRequest.getCompanyId() != null? transactionRequest.getCompanyId(): "")
                .build();
    }
}
