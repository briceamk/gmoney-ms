package cm.g2s.loan.sm.action;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.service.LoanService;
import cm.g2s.loan.service.broker.publisher.LoanEventPublisherService;
import cm.g2s.loan.service.broker.payload.DebitAccountRequest;
import cm.g2s.loan.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebitAccountAction implements Action<LoanState, LoanEvent> {
    private final LoanService loanService;
    private final LoanEventPublisherService publisherService;
    @Override
    public void execute(StateContext<LoanState, LoanEvent> context) {

        String loanId = (String) context.getMessage().getHeaders().getOrDefault(LoanConstantType.LOAN_ID_HEADER, "");

        if(!loanId.isEmpty()) {
            Loan loan = loanService.findById(null, loanId);
            if(loan != null) {
                DebitAccountRequest accountRequest = transform(loan);
                log.info("Sending Validated passed actions request to the queue for loanId: {}", loanId);
                publisherService.onDebitAccountEvent(accountRequest);
            }

        } else {
            log.info("LOAN_ID_HEADER have not send with the event {}",context.getEvent().name());
            throw new BadRequestException(String.format("LOAN_ID_HEADER have not send with the event %s",context.getEvent().name()));
        }


    }

    private DebitAccountRequest transform(Loan loan) {
        return DebitAccountRequest.builder()
                .loanId(loan.getId())
                .accountId(loan.getAccountId())
                .debitAmount(loan.getAmount().add(loan.getInterest()))
                .build();
    }
}
