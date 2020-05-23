package cm.g2s.transaction.service.loan;

import cm.g2s.transaction.service.loan.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "loan", fallback = LoanClientServiceFallBack.class)
public interface LoanClientService {
    @GetMapping("/api/v1/loans/{id}")
    LoanDto findById(@PathVariable String id);
}
