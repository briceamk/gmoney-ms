package cm.g2s.loan.service.rule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service("ruleClientService")
public class RuleClientServiceImpl implements RuleClientService {

    private final RuleClientService ruleClientService;

    @Override
    public Map<String, BigDecimal> processInterest(String id, Long numberOfDays, BigDecimal amount) throws ScriptException {
        log.info("Calling rule-service api from loan-service");
        return ruleClientService.processInterest(id, numberOfDays, amount);
    }
}
