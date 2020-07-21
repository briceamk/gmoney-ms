package cm.g2s.loan.service.impl;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.loan.service.broker.payload.CreateSendMoneySuccessEmailResponse;
import cm.g2s.loan.service.broker.payload.SendMoneyResponse;
import cm.g2s.loan.service.partner.model.PartnerDto;
import cm.g2s.loan.exception.BadRequestException;
import cm.g2s.loan.sm.LoanStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service("loanManagerService")
public class LoanManagerServiceImpl implements LoanManagerService {


    private final LoanService loanService;
    private final StateMachineFactory<LoanState, LoanEvent> machineFactory;
    private final LoanStateChangeInterceptor loanStateChangeInterceptor;

    @Override
    @Transactional
    public void validateLoan(CustomPrincipal principal, Loan loan, AccountDto accountDto, PartnerDto partnerDto) {
        //We first check if account is not in PENDING state. In this state, loan can't be create
        if(accountDto.getState().equals(LoanConstantType.ACCOUNT_STATE_PENDING)) {
            log.info("You can not send a request when your account is in pending state");
            throw new BadRequestException("You can not send a request when your account is in pending state");
        }

        loan = loanService.validateLoan(principal, loan);

        //send event VALIDATE_LOAN to start validation process. no action needed
        sendEvent(loan, LoanEvent.VALIDATE_LOAN, principal, null);

        //await for status change
        awaitForStatusChange(principal, loan.getId(), LoanState.VALID_PENDING);

        //We retrieve de pending loan
        Loan validatePendingLoan = loanService.findById(principal, loan.getId());

        BigDecimal currentCreditLimit = accountDto.getBalance().add(validatePendingLoan.getAmount()).add(validatePendingLoan.getInterest());
        if(partnerDto.getCreditLimit() != null && partnerDto.getCreditLimit().compareTo(currentCreditLimit) < 0  ||
                    partnerDto.getCategoryDto().getCreditLimit().compareTo(currentCreditLimit) < 0) {
            //send event VALIDATE_LOAN_FAILED. not action needed
            sendEvent(validatePendingLoan, LoanEvent.VALIDATE_LOAN_FAILED, principal, null);

            //await for status change
            awaitForStatusChange(principal, validatePendingLoan.getId(), LoanState.VALID_EXCEPTION);

            log.error("With this loan request your credit limit is exceeded by {}",
                    currentCreditLimit.subtract(partnerDto.getCreditLimit()));

            throw new BadRequestException(String.format("With this loan request your credit limit is exceeded by %s",
                    currentCreditLimit.subtract(partnerDto.getCreditLimit())));
        }
        //send Event VALIDATE_LOAN_PASSED. not action needed
        sendEvent(validatePendingLoan, LoanEvent.VALIDATE_LOAN_PASSED, principal, null);

        //await for status change
        awaitForStatusChange(principal, validatePendingLoan.getId(), LoanState.VALID);

        //We retrieve de pending loan
        Loan validLoan = loanService.findById(principal, validatePendingLoan.getId());

        //send Event DEBIT_ACCOUNT. need to define Send Money Action
        sendEvent(validLoan, LoanEvent.DEBIT_ACCOUNT, principal, null);
    }

    @Override
    @Transactional
    public void processDebitAccountResponse(CustomPrincipal principal, String loanId, Boolean debitAccountError) {
        log.info("Process debit account action result");
        Loan loan = loanService.findById(principal, loanId);

        if(loan != null) {
            if(!debitAccountError) {
                //We send event to change the state from ACCOUNT_DEBIT_PENDING to ACCOUNT_DEBIT
                // not action needed
                sendEvent(loan, LoanEvent.DEBIT_ACCOUNT_PASSED, principal, null);

                //await for status change
                awaitForStatusChange(principal, loanId, LoanState.ACCOUNT_DEBIT);

                Loan debitedLoan = loanService.findById(principal, loan.getId());

                //We send event to change the state from ACCOUNT_DEBIT to TRANSACTION_CREATED_PENDING
                // CreateTransactionAction needed
                sendEvent(debitedLoan, LoanEvent.CREATE_TRANSACTION, principal, null);

            } else {
                sendEvent(loan, LoanEvent.DEBIT_ACCOUNT_FAILED, principal, null);

                //TODO execute compensations actions
            }
        }

    }

    @Override
    @Transactional
    public void processCreateTransactionResponse(CustomPrincipal principal, String loanId, Boolean createTransactionError) {
        log.info("Process debit account action result");
        Loan loan = loanService.findById(principal, loanId);

        if(loan != null) {
            if(!createTransactionError) {
                //We send event to change the state from TRANSACTION_CREATED_PENDING to TRANSACTION_CREATED
                // not action needed
                sendEvent(loan, LoanEvent.CREATE_TRANSACTION_PASSED, principal, null);

                //await for status change
                awaitForStatusChange(principal, loanId, LoanState.TRANSACTION_CREATED);

                Loan transactionCreatedLoan = loanService.findById(principal, loan.getId());

                //We send event to change the state from TRANSACTION_CREATED to TRANSACTION_SEND_PENDING
                sendEvent(transactionCreatedLoan, LoanEvent.SEND_TRANSACTION, principal, null);

            } else {
                sendEvent(loan, LoanEvent.CREATE_TRANSACTION_FAILED, principal, null);

                //TODO execute compensations actions
                //1 - We send event to Credit Account and set state account to ACTIVE
                //2- We set error message and state of transaction


            }
        }
    }

    @Override
    @Transactional
    public void processSendMoneyResponse(CustomPrincipal principal, String loanId, SendMoneyResponse response) {
        log.info("Process send money action result");
        Loan loan = loanService.findById(principal, loanId);

        if(loan != null) {
            if(!response.getSendMoneyError()) {
                //We send event to change the state from TRANSACTION_SEND_PENDING to TRANSACTION_SEND
                // not action needed
                sendEvent(loan, LoanEvent.SEND_TRANSACTION_PASSED, principal, null);

                //await for status change
                awaitForStatusChange(principal, loanId, LoanState.TRANSACTION_SEND);

                Loan sendCreatedLoan = loanService.findById(principal, loan.getId());

                //We send event to change the state from TRANSACTION_SEND to CONFIRM_DEBIT_PENDING
                sendEvent(sendCreatedLoan, LoanEvent.CONFIRM_DEBIT, principal, null);

            } else {
                sendEvent(loan, LoanEvent.SEND_TRANSACTION_FAILED, principal, null);

                //TODO execute compensations actions:
                //1 - We send event to Credit Account and set state account to ACTIVE

                //2- We set error message and state of transaction


            }
        }
    }

    @Override
    @Transactional
    public void processConfirmAccountDebitResponse(CustomPrincipal principal, String loanId, ConfirmDebitAccountResponse response) {
        log.info("Process confirm debit account action result");
        Loan loan = loanService.findById(null, loanId);

        if(loan != null) {
            if(!response.getConfirmDebitAccountError()) {
                //We send event to change the state from CONFIRM_DEBIT_PENDING to CONFIRM_DEBIT
                // not action needed
                sendEvent(loan, LoanEvent.CONFIRM_DEBIT_PASSED, principal, null);

                //await for status change
                awaitForStatusChange(principal, loanId, LoanState.CONFIRM_DEBIT);

                Loan confirmedLoan = loanService.findById(principal, loan.getId());

                //We send event to change the state from CONFIRM_DEBIT to EMAIL_NOTIFICATION_CREATE_PENDING
                // CreateTransactionAction needed
                sendEvent(confirmedLoan, LoanEvent.CREATE_EMAIL_NOTIFICATION, principal, response);

            } else {
                sendEvent(loan, LoanEvent.CONFIRM_DEBIT_FAILED, principal, null);

                //TODO execute compensations actions:
                //1 - We send a notification to manager to manually activate the account

                //2- We set error message and state of transaction


            }
        }
    }

    @Override
    @Transactional
    public void processCreateSendMoneySuccessEmailResponse(CustomPrincipal principal, String loanId, CreateSendMoneySuccessEmailResponse response) {
        log.info("Process create send money success email response action result");
        Loan loan = loanService.findById(null, loanId);

        if(loan != null) {
            if(!response.getCreateSendMoneySuccessEmailError()) {
                //We send event to change the state from EMAIL_NOTIFICATION_CREATE_PENDING to EMAIL_NOTIFICATION_CREATE
                // not action needed
                sendEvent(loan, LoanEvent.CREATE_EMAIL_NOTIFICATION_PASSED, principal, null);

                //await for status change
                awaitForStatusChange(principal, loanId, LoanState.EMAIL_NOTIFICATION_CREATE);

                Loan notificationCreatedLoan = loanService.findById(principal, loan.getId());

                //We send event to change the state from EMAIL_NOTIFICATION_CREATE to TERMINATE
                sendEvent(notificationCreatedLoan, LoanEvent.TERMINATE, principal, response);

            } else {
                sendEvent(loan, LoanEvent.CREATE_EMAIL_NOTIFICATION_FAILED, principal, null);

                //TODO execute compensations actions:
                //1 - We send a notification to manager to check why email was not create

                //2- We set error message and state of transaction


            }
        }
    }

    private void awaitForStatusChange(CustomPrincipal principal, String loanId, LoanState state) {
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while (!found.get()) {
            if(loopCount.incrementAndGet() > 10) {
                found.set(true);
                log.debug("Loop retries exceeded!");
            }

            Loan loan = loanService.findById(principal, loanId);
            if(loan != null) {
                if(loan.getState().equals(state)) {
                    found.set(true);
                    log.debug("Loan found!");
                } else {
                    log.debug("Loan state not equals. Expected: {}, Found: {}", state, loan.getState().name());
                }
            }

            if(!found.get()) {
                try {
                    log.debug("Sleeping for retry");
                    Thread.sleep(100);
                } catch (Exception e) {
                    // do nothing
                }
            }

        }
    }

    private void sendEvent(Loan loan, LoanEvent event, CustomPrincipal principal, Object payload) {
        StateMachine<LoanState, LoanEvent> machine = build(loan);
        ConfirmDebitAccountResponse confirmDebitAccountResponse = null;

        if(payload instanceof ConfirmDebitAccountResponse)
            confirmDebitAccountResponse = (ConfirmDebitAccountResponse) payload;



        MessageBuilder messageBuilder = MessageBuilder.withPayload(event)
                .setHeader(LoanConstantType.LOAN_ID_HEADER, loan.getId())
                .setHeader(LoanConstantType.PRINCIPAL_ID_HEADER, principal);

        if(confirmDebitAccountResponse != null) {
            messageBuilder.setHeader(LoanConstantType.ACCOUNT_BALANCE_HEADER, confirmDebitAccountResponse.getBalance());
        }

        machine.sendEvent(messageBuilder.build());
    }



    private StateMachine<LoanState, LoanEvent> build(Loan loan) {
        StateMachine<LoanState, LoanEvent> machine = machineFactory.getStateMachine(loan.getId());

        machine.stop();

        machine.getStateMachineAccessor()
                .doWithAllRegions(machineAccess -> {
                    machineAccess.addStateMachineInterceptor(loanStateChangeInterceptor);
                    machineAccess.resetStateMachine(new DefaultStateMachineContext<>(loan.getState(), null, null, null));
                });

        machine.start();

        return machine;
    }
}
