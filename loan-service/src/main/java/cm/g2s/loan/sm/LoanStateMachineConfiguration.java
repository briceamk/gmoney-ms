package cm.g2s.loan.sm;

import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.sm.action.DebitAccountAction;
import cm.g2s.loan.sm.action.CreateTransactionAction;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@RequiredArgsConstructor
@EnableStateMachineFactory
public class LoanStateMachineConfiguration extends StateMachineConfigurerAdapter<LoanState, LoanEvent> {

    private final CreateTransactionAction createTransactionAction;
    private final DebitAccountAction debitAccountAction;

    @Override
    public void configure(StateMachineStateConfigurer<LoanState, LoanEvent> states) throws Exception {
        states.withStates()
                .initial(LoanState.DRAFT)
                .states(EnumSet.allOf(LoanState.class))
                .end(LoanState.DONE)
                .end(LoanState.VALID_EXCEPTION)
                .end(LoanState.ACCOUNT_DEBIT_EXCEPTION)
                .end(LoanState.TRANSACTION_CREATED_EXCEPTION)
                .end(LoanState.TRANSACTION_SEND_EXCEPTION)
                .end(LoanState.CONFIRM_DEBIT_EXCEPTION)
                .end(LoanState.NOTIFICATION_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<LoanState, LoanEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(LoanState.DRAFT)
                .target(LoanState.VALID_PENDING)
                .event(LoanEvent.VALIDATE_LOAN)
            .and().withExternal()
                .source(LoanState.VALID_PENDING)
                .target(LoanState.VALID)
                .event(LoanEvent.VALIDATE_LOAN_PASSED)
            .and().withExternal()
                .source(LoanState.VALID_PENDING)
                .target(LoanState.VALID_EXCEPTION)
                .event(LoanEvent.VALIDATE_LOAN_FAILED)
            .and().withExternal()
                .source(LoanState.VALID)
                .target(LoanState.ACCOUNT_DEBIT_PENDING)
                .event(LoanEvent.DEBIT_ACCOUNT)
                .action(debitAccountAction)
            .and().withExternal()
                .source(LoanState.ACCOUNT_DEBIT_PENDING)
                .target(LoanState.ACCOUNT_DEBIT)
                .event(LoanEvent.DEBIT_ACCOUNT_PASSED)
            .and().withExternal()
                .source(LoanState.ACCOUNT_DEBIT_PENDING)
                .target(LoanState.ACCOUNT_DEBIT_EXCEPTION)
                .event(LoanEvent.DEBIT_ACCOUNT_FAILED)
            .and().withExternal()
                .source(LoanState.ACCOUNT_DEBIT)
                .target(LoanState.TRANSACTION_CREATED_PENDING)
                .event(LoanEvent.CREATE_TRANSACTION)
                .action(createTransactionAction)
            .and().withExternal()
                .source(LoanState.TRANSACTION_CREATED_PENDING)
                .target(LoanState.TRANSACTION_CREATED)
                .event(LoanEvent.CREATE_TRANSACTION_PASSED)
            .and().withExternal()
                .source(LoanState.TRANSACTION_CREATED_PENDING)
                .target(LoanState.TRANSACTION_CREATED_EXCEPTION)
                .event(LoanEvent.CREATE_TRANSACTION_FAILED)
            .and().withExternal()
                .source(LoanState.TRANSACTION_CREATED)
                .target(LoanState.TRANSACTION_SEND_PENDING)
                .event(LoanEvent.SEND_TRANSACTION)
            .and().withExternal()
                .source(LoanState.TRANSACTION_SEND_PENDING)
                .target(LoanState.TRANSACTION_SEND)
                .event(LoanEvent.SEND_TRANSACTION_PASSED)
            .and().withExternal()
                .source(LoanState.TRANSACTION_SEND_PENDING)
                .target(LoanState.TRANSACTION_SEND_EXCEPTION)
                .event(LoanEvent.SEND_TRANSACTION_FAILED)
            .and().withExternal()
                .source(LoanState.TRANSACTION_SEND)
                .target(LoanState.CONFIRM_DEBIT)
                .event(LoanEvent.CONFIRM_DEBIT_PASSED)
            .and().withExternal()
                .source(LoanState.CONFIRM_DEBIT_PENDING)
                .target(LoanState.CONFIRM_DEBIT_EXCEPTION)
                .event(LoanEvent.CONFIRM_DEBIT_FAILED)
            .and().withExternal()
                .source(LoanState.CONFIRM_DEBIT)
                .target(LoanState.NOTIFICATION_SEND_PENDING)
                .event(LoanEvent.SEND_NOTIFICATION)
                .and().withExternal()
                .source(LoanState.NOTIFICATION_SEND_PENDING)
                .target(LoanState.NOTIFICATION_SEND)
                .event(LoanEvent.SEND_NOTIFICATION_PASSED)
                .and().withExternal()
                .source(LoanState.NOTIFICATION_SEND_PENDING)
                .target(LoanState.NOTIFICATION_EXCEPTION)
                .event(LoanEvent.SEND_NOTIFICATION_FAILED)
                .and().withExternal()
                .source(LoanState.NOTIFICATION_SEND)
                .target(LoanState.DONE)
                .event(LoanEvent.TERMINATE);

    }
}
