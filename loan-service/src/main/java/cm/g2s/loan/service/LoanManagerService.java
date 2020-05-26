package cm.g2s.loan.service;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.loan.service.broker.payload.SendMoneyResponse;
import cm.g2s.loan.service.partner.model.PartnerDto;

public interface LoanManagerService {
    void validateLoan(CustomPrincipal principal, Loan loan, AccountDto accountDto, PartnerDto partnerDto);

    void processDebitAccountResponse(CustomPrincipal principal, String loanId, Boolean debitAccountError);

    void processCreateTransactionResponse(CustomPrincipal principal, String loanId, Boolean createTransactionError);

    void processSendMoneyResponse(CustomPrincipal principal, String loanId, SendMoneyResponse response);

    void processConfirmAccountDebitResponse(CustomPrincipal principal, String loanId,
                                            ConfirmDebitAccountResponse confirmDebitAccountResponse);
}
