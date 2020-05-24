package cm.g2s.loan.service.partner.service;

import cm.g2s.loan.service.partner.model.PartnerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "partner", fallback = PartnerClientServiceFallBack.class)
public interface PartnerClientService {
    @GetMapping("/partner/api/v1/partners/{id}")
    PartnerDto findById(@PathVariable String id);
}
