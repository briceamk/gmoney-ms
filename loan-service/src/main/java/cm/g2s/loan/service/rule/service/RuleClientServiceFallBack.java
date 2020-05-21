package cm.g2s.loan.service.rule.service;

import cm.g2s.loan.constant.LoanConstantType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class RuleClientServiceFallBack implements RuleClientService{
    @Override
    public Map<String, BigDecimal> processInterest(String id, Long numberOfDays, BigDecimal amount) throws ScriptException {
        log.error("Error when calling rule-service api from loan-service");
        Map<String, BigDecimal> interestMap = new LinkedHashMap<>();
        interestMap.put(LoanConstantType.INTEREST_KEY, BigDecimal.ZERO);
        return interestMap;
    }
}
