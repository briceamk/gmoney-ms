package cm.g2s.loan.sm.action;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.exception.BadRequestException;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.broker.payload.CreateSendMoneySuccessEmailRequest;
import cm.g2s.loan.service.broker.service.publisher.LoanEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateEmailNotificationAction implements Action<LoanState, LoanEvent> {
    private final LoanService loanService;
    private final LoanEventPublisherService publisherService;
    @Override
    public void execute(StateContext<LoanState, LoanEvent> context) {

        String loanId = (String) context.getMessage().getHeaders().getOrDefault(LoanConstantType.LOAN_ID_HEADER, "");
        BigDecimal balance = (BigDecimal) context.getMessage().getHeaders().getOrDefault(LoanConstantType.ACCOUNT_BALANCE_HEADER, null);
        if(!loanId.isEmpty()) {
            Loan loan = loanService.findById(null, loanId);
            if(loan != null) {
                if(balance != null) {
                    CreateSendMoneySuccessEmailRequest createSendMoneySuccessEmailRequest = transform(loan, balance);
                    log.info("Sending success send money email action request to the queue for loanId: {}", loanId);
                    publisherService.onCreateSendMoneySuccessEmailEvent(createSendMoneySuccessEmailRequest);

                } else {
                    log.info("ACCOUNT_BALANCE_HEADER have not send with the event {}",context.getEvent().name());
                    throw new BadRequestException(String.format("ACCOUNT_BALANCE_HEADER have not send with the event %s",context.getEvent().name()));
                }

            }

        } else {
            log.info("LOAN_ID_HEADER have not send with the event {}",context.getEvent().name());
            throw new BadRequestException(String.format("LOAN_ID_HEADER have not send with the event %s",context.getEvent().name()));
        }


    }

    private CreateSendMoneySuccessEmailRequest transform(Loan loan, BigDecimal balance) {

        return CreateSendMoneySuccessEmailRequest.builder()
                .loanId(loan.getId())
                .loanNumber(loan.getNumber())
                .amount(loan.getAmount().add(loan.getInterest()))
                .balance(balance)
                .type("SEND_MONEY_SUCCESS")
                .fullName(loan.getFullName())
                .email(loan.getEmail())
                .build();
    }
}
