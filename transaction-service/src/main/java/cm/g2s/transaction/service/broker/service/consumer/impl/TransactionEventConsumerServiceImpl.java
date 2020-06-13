package cm.g2s.transaction.service.broker.service.consumer.impl;

import cm.g2s.transaction.domain.model.Transaction;
import cm.g2s.transaction.domain.model.TransactionMode;
import cm.g2s.transaction.domain.model.TransactionState;
import cm.g2s.transaction.service.TransactionService;
import cm.g2s.transaction.service.broker.service.consumer.TransactionEventConsumerService;
import cm.g2s.transaction.service.broker.payload.*;
import cm.g2s.transaction.service.broker.service.publisher.TransactionEventPublisherService;
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
    @StreamListener(value = "loanChannel", condition = "headers['loan']=='createTransaction'")
    public void observeCreateTransactionRequest(@Payload  CreateTransactionRequest createTransactionRequest) {
        CreateTransactionResponse.CreateTransactionResponseBuilder builder = CreateTransactionResponse.builder();
        log.info("Receiving Create Transaction Request from loan-service");
        try {
            Transaction transaction = transform(createTransactionRequest);
            transactionService.create(null, transaction);
            builder.loanId(transaction.getLoanId())
                    .createTransactionError(false);
            log.info(" Create Transaction Request Successfully");
        }catch (Exception e) {
            log.error("Error when creating transaction with data from loan-service");
            builder.loanId(createTransactionRequest.getLoanId())
                    .createTransactionError(true);
        }finally {
            //Sending Response to uaa-service
            publisherService.onCreateTransactionResponseEvent(builder.build());
        }
    }

    @Override
    @StreamListener(value = "cronChannel", condition = "headers['cron']=='sendMoney'")
    public void observeSendMoneyRequest(@Payload JobRequest jobRequest) {
        log.info("Receiving Send Money Request from cron-service");
        if(jobRequest.getEventType().equals(JobType.SEND_MONEY)) {
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

    private Transaction transform(CreateTransactionRequest createTransactionRequest) {
        return  Transaction.builder()
                .originRef(createTransactionRequest.getOriginRef())
                .mobile(createTransactionRequest.getMobile())
                .amount(createTransactionRequest.getAmount())
                .issueDate(createTransactionRequest.getIssueDate())
                .mode(TransactionMode.valueOf(createTransactionRequest.getMode()))
                .loanId(createTransactionRequest.getLoanId() != null? createTransactionRequest.getLoanId() : "")
                .partnerId(createTransactionRequest.getPartnerId())
                .userId(createTransactionRequest.getUserId())
                .accountId(createTransactionRequest.getAccountId())
                .companyId(createTransactionRequest.getCompanyId() != null? createTransactionRequest.getCompanyId(): "")
                .build();
    }
}
