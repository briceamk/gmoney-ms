package cm.g2s.loan.service.rule.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;

@FeignClient(value = "rule", fallback = RuleClientServiceFallBack.class)
public interface RuleClientService {
    @GetMapping("/rule/api/v1/rules/interest/amount/{amount}/numberOfDays/{numberOfDays}/id/{id}")
    Map<String, BigDecimal>  processInterest(@PathVariable String id, @PathVariable Long numberOfDays,
                                             @PathVariable BigDecimal amount) throws ScriptException;
}
