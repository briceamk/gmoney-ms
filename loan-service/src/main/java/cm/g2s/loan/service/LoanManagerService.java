package cm.g2s.loan.service;

import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.shared.dto.LoanDto;

public interface LoanManagerService {
    void validateLoan(CustomPrincipal principal, LoanDto loanDto);

    void processDebitAccountResponse(String loanId, Boolean debitAccountError);
}
