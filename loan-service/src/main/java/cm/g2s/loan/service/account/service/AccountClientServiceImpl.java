package cm.g2s.loan.service.account.service;

import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.partner.model.PartnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("accountClientService")
public class AccountClientServiceImpl implements AccountClientService {

    private final AccountClientService accountClientService;

    @Override
    public AccountDto findById(String userId) {
        log.info("calling account-service api from loan-service...");
        return accountClientService.findById(userId);
    }
}
