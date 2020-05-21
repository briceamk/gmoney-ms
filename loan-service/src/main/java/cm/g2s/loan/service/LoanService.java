package cm.g2s.loan.service;

import cm.g2s.loan.security.CustomPrincipal;
import cm.g2s.loan.shared.dto.LoanDto;
import cm.g2s.loan.shared.dto.LoanDtoPage;
import org.springframework.data.domain.PageRequest;

import javax.script.ScriptException;

public interface LoanService {
    LoanDto create(CustomPrincipal principal, LoanDto loanDto) throws ScriptException;

    void update(CustomPrincipal principal, LoanDto loanDto) throws ScriptException;

    LoanDto findById(CustomPrincipal principal, String id);

    LoanDtoPage findAll(CustomPrincipal principal, String number,
                        String state, String partnerId, String accountId, PageRequest pageRequest);

    void deleteById(CustomPrincipal principal, String id);

    void validateLoan(CustomPrincipal principal, LoanDto loanDto);
}
