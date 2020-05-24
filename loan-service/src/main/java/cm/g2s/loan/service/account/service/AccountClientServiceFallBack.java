package cm.g2s.loan.service.account.service;

import cm.g2s.loan.service.account.model.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountClientServiceFallBack implements AccountClientService {
    @Override
    public AccountDto findById(String id) {
        log.error("Error when call account-service api from loan service...");
        return null;
    }
}
