package cm.g2s.loan.sm.action;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.event.LoanEvent;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.infrastructure.repository.LoanRepository;
import cm.g2s.loan.service.broker.publisher.LoanEventPublisherService;
import cm.g2s.loan.shared.dto.LoanDto;
import cm.g2s.loan.shared.exception.BadRequestException;
import cm.g2s.loan.shared.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMoneyAction implements Action<LoanState, LoanEvent> {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final LoanEventPublisherService publisherService;
    @Override
    public void execute(StateContext<LoanState, LoanEvent> context) {

        String loanId = (String) context.getMessage().getHeaders().getOrDefault(LoanConstantType.LOAN_ID_HEADER, "");

        if(!loanId.isEmpty()) {
            LoanDto loanDto = loanMapper.map(loanRepository.getOne(loanId));
            log.info("Sending Validated passed actions request to the queue for loanId: {}", loanId);
            publisherService.onSendMoneyEvent(loanDto);
        } else {
            log.info("LOAN_ID_HEADER have not send with the event {}",context.getEvent().name());
            throw new  BadRequestException(String.format("LOAN_ID_HEADER have not send with the event %s",context.getEvent().name()));
        }


    }
}
