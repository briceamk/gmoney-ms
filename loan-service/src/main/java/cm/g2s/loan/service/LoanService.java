package cm.g2s.loan.service;

import cm.g2s.loan.domain.model.Loan;
import cm.g2s.loan.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.script.ScriptException;

public interface LoanService {
    Loan create(CustomPrincipal principal, Loan loan) throws ScriptException;

    void update(CustomPrincipal principal, Loan loan) throws ScriptException;

    Loan findById(CustomPrincipal principal, String id);

    Page<Loan> findAll(CustomPrincipal principal, String number,
                 String state, String partnerId, String accountId, PageRequest pageRequest);

    void deleteById(CustomPrincipal principal, String id);

    Loan validateLoan(CustomPrincipal principal, Loan loan);
}
