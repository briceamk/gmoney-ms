package cm.g2s.loan.sm;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanStateChangeInterceptor extends StateMachineInterceptorAdapter<LoanState, LoanEvent> {

    private final LoanService loanService;

    @SneakyThrows
    @Override
    public void preStateChange(State<LoanState, LoanEvent> state, Message<LoanEvent> message,
                               Transition<LoanState, LoanEvent> transition, StateMachine<LoanState, LoanEvent> stateMachine) {
        log.info("Loan State change....");

        String loanId = (String) message.getHeaders().getOrDefault(LoanConstantType.LOAN_ID_HEADER, "");
        CustomPrincipal principal = (CustomPrincipal) message.getHeaders().getOrDefault(LoanConstantType.PRINCIPAL_ID_HEADER, null);
        if(!loanId.isEmpty()) {
            log.info("Saving state for loanId: {} Status: {}",loanId, state.getId());
            Loan loan = loanService.findById(null, loanId);
            if(state.getId().equals(LoanState.VALID) && loan.getNumber().equals(LoanConstantType.NEW_LOAN_NUMBER)) {
                //TODO add a table to save sequence of Loan
                LocalDateTime currentTime = LocalDateTime.now();
                String number = String.valueOf(currentTime.getYear()) + String.valueOf(currentTime.getMonthValue()) +
                        String.valueOf(currentTime.getDayOfMonth()) + String.valueOf(currentTime.getHour()) +
                        String.valueOf(currentTime.getMinute()) +  String.valueOf(currentTime.getSecond());
                loan.setNumber(number);
            }
            //We set the next state
            loan.setState(state.getId());
            loanService.update(null, loan);

        }
    }
}
