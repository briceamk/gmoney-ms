package cm.g2s.account.service;

import cm.g2s.account.shared.dto.AccountDto;
import cm.g2s.account.shared.dto.AccountDtoPage;
import org.springframework.data.domain.PageRequest;

public interface AccountService {
    AccountDto create(AccountDto accountDto);

    void update(AccountDto accountDto);

    AccountDto findById(String id);

    AccountDto findByNumber(String number);

    AccountDto findByPartnerId(String partnerId);

    AccountDtoPage findAll(String number, String partnerId, PageRequest of);

    void deleteById(String id);
}
