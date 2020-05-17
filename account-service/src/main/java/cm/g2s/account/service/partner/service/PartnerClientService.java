package cm.g2s.account.service.partner.service;

import cm.g2s.account.service.partner.model.PartnerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "partner", fallback = PartnerClientServiceFallBack.class)
public interface PartnerClientService {
    @GetMapping("/api/v1/partners/{id}")
    PartnerDto findById(@PathVariable String id);
}
