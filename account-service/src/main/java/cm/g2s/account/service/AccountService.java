package cm.g2s.account.service;

import cm.g2s.account.domain.model.Account;
import cm.g2s.account.domain.model.AccountState;
import cm.g2s.account.security.CustomPrincipal;
import cm.g2s.account.service.partner.model.PartnerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

public interface AccountService {
    Account create(CustomPrincipal principal, Account account, PartnerDto partnerDto);

    void update(CustomPrincipal principal, Account account);

    Account findById(CustomPrincipal principal, String id);

    Account findByNumber(CustomPrincipal principal, String number);

    Account findByPartnerId(CustomPrincipal principal, String partnerId);

    Page<Account> findAll(CustomPrincipal principal, String number, String key, String partnerId, PageRequest pageRequest);

    void deleteById(CustomPrincipal principal, String id);

    void debitAccount(CustomPrincipal principal, String accountId, BigDecimal debitAmount);

    void confirmDeBitAccount(CustomPrincipal principal, String accountId, AccountState state);

    Account findByUserId(CustomPrincipal principal, String userId);
}
