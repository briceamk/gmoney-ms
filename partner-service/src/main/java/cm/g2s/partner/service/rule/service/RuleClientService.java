package cm.g2s.partner.service.rule.service;

import cm.g2s.partner.service.rule.RuleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "rule", fallback = RuleClientServiceFallBack.class)
public interface RuleClientService {

    @GetMapping("/api/v1/rules/{id}")
    RuleDto findById(@PathVariable String id);
}
