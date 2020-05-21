package cm.g2s.loan.service.impl;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.infrastructure.repository.LoanRepository;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanManagerService;
import cm.g2s.loan.shared.dto.LoanDto;
import cm.g2s.loan.shared.exception.BadRequestException;
import cm.g2s.loan.shared.mapper.LoanMapper;
import cm.g2s.loan.sm.LoanStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service("loanManagerService")
public class LoanManagerServiceImpl implements LoanManagerService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final StateMachineFactory<LoanState, LoanEvent> machineFactory;
    private final LoanStateChangeInterceptor loanStateChangeInterceptor;

    @Override
    @Transactional
    public void validateLoan(CustomPrincipal principal, LoanDto loanDto) {

        //send event VALIDATE_LOAN to start validation process. no action needed
        sendEvent(loanDto, LoanEvent.VALIDATE_LOAN, principal, null);

        //await for status change
        awaitForStatusChange(loanDto.getId(), LoanState.VALID_PENDING);

        //We retrieve de pending loan
        LoanDto pendingLoanDto = loanMapper.map(loanRepository.getOne(loanDto.getId()));

        // We check if with this loan credit limit will be exceeded
        if(pendingLoanDto.getAccountDto() == null ) {
            log.error("NetWork problem, please retry");
            throw new BadRequestException("NetWork problem, please retry");
        }
        BigDecimal currentCreditLimit = pendingLoanDto.getAccountDto().getBalance().add(pendingLoanDto.getAmount()).add(pendingLoanDto.getInterest());
        if(pendingLoanDto.getPartnerDto().getCreditLimit().compareTo(currentCreditLimit) < 0 ) {
            //send event VALIDATE_LOAN_FAILED. not action needed
            sendEvent(pendingLoanDto, LoanEvent.VALIDATE_LOAN_FAILED, principal, null);

            //await for status change
            awaitForStatusChange(pendingLoanDto.getId(), LoanState.VALID_EXCEPTION);
            log.error("With this loan request your credit limit is exceeded by {}",currentCreditLimit.subtract(pendingLoanDto.getPartnerDto().getCreditLimit()));
            throw new BadRequestException(String.format("With this loan request your credit limit is exceeded by %s",currentCreditLimit.subtract(pendingLoanDto.getPartnerDto().getCreditLimit())));
        }
        //send Event VALIDATE_LOAN_PASSED. not action needed
        sendEvent(pendingLoanDto, LoanEvent.VALIDATE_LOAN_PASSED, principal, null);

        //await for status change
        awaitForStatusChange(loanDto.getId(), LoanState.VALID);

        //We retrieve de pending loan
        LoanDto validLoanDto = loanMapper.map(loanRepository.getOne(pendingLoanDto.getId()));

        //send Event ACCOUNT_DEBIT. need to define Send Money Action
        sendEvent(validLoanDto, LoanEvent.ACCOUNT_DEBIT, principal, null);
    }

    @Override
    @Transactional
    public void processDebitAccountResponse(String loanId, Boolean debitAccountError) {
        log.info("Process debit account action result");
        Optional<Loan> optionalLoan = loanRepository.findById(loanId);

        optionalLoan.ifPresent(loan -> {
            if(!debitAccountError) {
                //We send event to change the state from DEBIT_ACCOUNT_PENDING to ACCOUNT_DEBIT
                // not action needed
                sendEvent(loanMapper.map(loan), LoanEvent.ACCOUNT_DEBIT_PASSED, null, null);

                //await for status change
                awaitForStatusChange(loanId, LoanState.ACCOUNT_DEBIT);

                Loan debitedLoan = loanRepository.getOne(loanId);

                //We send event to change the state from ACCOUNT_DEBIT to SEND_MONEY_PENDING
                // SendMoneyAction needed
                sendEvent(loanMapper.map(debitedLoan), LoanEvent.MONEY_SEND, null, null);

            } else {
                sendEvent(loanMapper.map(loan), LoanEvent.ACCOUNT_DEBIT_FAILED, null, null);

                //TODO execute compensations actions
            }
        });

    }

    private void awaitForStatusChange(String loanId, LoanState state) {
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while (!found.get()) {
            if(loopCount.incrementAndGet() > 10) {
                found.set(true);
                log.debug("Loop retries exceeded!");
            }

            loanRepository.findById(loanId).ifPresent(loan -> {
                if(loan.getState().equals(state)) {
                    found.set(true);
                    log.debug("Loan found!");
                } else {
                    log.debug("Loan state not equals. Expected: {}, Found: {}", state, loan.getState().name());
                }
            });

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

    private void sendEvent(LoanDto loanDto, LoanEvent event, CustomPrincipal principal, Object payload) {
        StateMachine<LoanState, LoanEvent> machine = build(loanDto);

        MessageBuilder messageBuilder = MessageBuilder.withPayload(event)
                .setHeader(LoanConstantType.LOAN_ID_HEADER, loanDto.getId())
                .setHeader(LoanConstantType.PRINCIPAL_ID_HEADER, principal);

        machine.sendEvent(messageBuilder.build());
    }



    private StateMachine<LoanState, LoanEvent> build(LoanDto loanDto) {
        StateMachine<LoanState, LoanEvent> machine = machineFactory.getStateMachine(loanDto.getId());

        machine.stop();

        machine.getStateMachineAccessor()
                .doWithAllRegions(machineAccess -> {
                    machineAccess.addStateMachineInterceptor(loanStateChangeInterceptor);
                    machineAccess.resetStateMachine(new DefaultStateMachineContext<>(LoanState.valueOf(loanDto.getState()), null, null, null));
                });

        machine.start();

        return machine;
    }
}
