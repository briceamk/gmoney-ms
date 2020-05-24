package cm.g2s.loan.service.account.service;

import cm.g2s.loan.service.account.model.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account", fallback = AccountClientServiceFallBack.class)
public interface AccountClientService {
    @GetMapping("/account/api/v1/accounts/{id}")
    AccountDto findById(@PathVariable String id);
}
