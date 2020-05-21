package cm.g2s.loan.service.broker.publisher;

import cm.g2s.loan.service.broker.payload.DebitAccount;
import cm.g2s.loan.shared.dto.LoanDto;

public interface LoanEventPublisherService {
    void onSendMoneyEvent(LoanDto loanDto);
    void onDebitAccountEvent(DebitAccount debitAccount);
}
