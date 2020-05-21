package cm.g2s.account.service;

import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.shared.dto.AccountDto;
import cm.g2s.account.shared.dto.AccountDtoPage;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface AccountService {
    AccountDto create(CustomPrincipal principal, AccountDto accountDto);

    void update(CustomPrincipal principal, AccountDto accountDto);

    AccountDto findById(CustomPrincipal principal, String id);

    AccountDto findByNumber(CustomPrincipal principal, String number);

    AccountDto findByPartnerId(CustomPrincipal principal, String partnerId);

    AccountDtoPage findAll(CustomPrincipal principal, String number, String partnerId, PageRequest of);

    void deleteById(CustomPrincipal principal, String id);

    void debitAccount(String accountId, BigDecimal debitAmount);

}
