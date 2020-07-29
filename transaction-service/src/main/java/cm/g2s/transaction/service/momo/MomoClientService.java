package cm.g2s.transaction.service.momo;

import cm.g2s.transaction.service.loan.LoanClientServiceFallBack;
import cm.g2s.transaction.service.momo.dto.TransferRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "momo")//, fallback = MomoClientServiceFallBack.class)
public interface MomoClientService {

    @PostMapping("/momo/api/v1/momos/transfer/{resourceId}")
    Object makeTransfer(@RequestBody @Valid TransferRequestDto transferRequest, @PathVariable String resourceId);

    @GetMapping("/momo/api/v1/momos/transfer/{resourceId}")
    Object findTransferInfo(@PathVariable String resourceId);


}
