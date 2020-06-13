package cm.g2s.uaa.sm;

import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.sm.action.CreateAccountAction;
import cm.g2s.uaa.sm.action.CreatePartnerAction;
import cm.g2s.uaa.sm.action.RemovePartnerAction;
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
public class UserStateMachineConfiguration extends StateMachineConfigurerAdapter<UserState, UserEvent> {

    private final CreatePartnerAction createPartnerAction;
    private final CreateAccountAction createAccountAction;
    private final RemovePartnerAction removePartnerAction;

    @Override
    public void configure(StateMachineStateConfigurer<UserState, UserEvent> states) throws Exception {
        states.withStates()
                .initial(UserState.NEW)
                .states(EnumSet.allOf(UserState.class))
                .end(UserState.USER_CREATED)
                .end(UserState.PARTNER_CREATED_EXCEPTION)
                .end(UserState.ACCOUNT_CREATED_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<UserState, UserEvent> transitions) throws Exception {
        transitions.withExternal()
                .source(UserState.NEW)
                .target(UserState.PARTNER_CREATED_PENDING)
                .event(UserEvent.CREATE_PARTNER)
                .action(createPartnerAction)
            .and().withExternal()
                .source(UserState.PARTNER_CREATED_PENDING)
                .target(UserState.PARTNER_CREATED)
                .event(UserEvent.CREATE_PARTNER_PASSED)
            .and().withExternal()
                .source(UserState.PARTNER_CREATED_PENDING)
                .target(UserState.PARTNER_CREATED_EXCEPTION)
                .event(UserEvent.CREATE_PARTNER_FAILED)
            .and().withExternal()
                .source(UserState.PARTNER_CREATED)
                .target(UserState.ACCOUNT_CREATED_PENDING)
                .event(UserEvent.CREATE_ACCOUNT)
                .action(createAccountAction)
            .and().withExternal()
                .source(UserState.ACCOUNT_CREATED_PENDING)
                .target(UserState.ACCOUNT_CREATED)
                .event(UserEvent.CREATE_ACCOUNT_PASSED)
            .and().withExternal()
                .source(UserState.ACCOUNT_CREATED_PENDING)
                .target(UserState.ACCOUNT_CREATED_EXCEPTION)
                .event(UserEvent.CREATED_ACCOUNT_FAILED)
                .action(removePartnerAction)
            .and().withExternal()
                .source(UserState.ACCOUNT_CREATED)
                .target(UserState.USER_CREATED)
                .event(UserEvent.CREATE_USER);


    }
}
