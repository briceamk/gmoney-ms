package cm.g2s.loan.service;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.partner.model.PartnerDto;

public interface LoanManagerService {
    void validateLoan(CustomPrincipal principal, Loan loan, AccountDto accountDto, PartnerDto partnerDto);

    void processDebitAccountResponse(CustomPrincipal principal, String loanId, Boolean debitAccountError);

    void processCreateTransactionResponse(CustomPrincipal principal, String loanId, Boolean createTransactionError);
}
