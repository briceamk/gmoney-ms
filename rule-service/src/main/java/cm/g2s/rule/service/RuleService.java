package cm.g2s.rule.service;

import cm.g2s.rule.security.CustomPrincipal;
import cm.g2s.rule.shared.dto.RuleDto;
import cm.g2s.rule.shared.dto.RuleDtoPage;
import org.springframework.data.domain.PageRequest;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;

public interface RuleService {
    RuleDto create(CustomPrincipal principal, RuleDto ruleDto);

    void update(CustomPrincipal principal, RuleDto ruleDto);

    RuleDto findById(CustomPrincipal principal, String id);

    RuleDtoPage findAll(CustomPrincipal principal, String code, String name, PageRequest of);

    void deleteById(CustomPrincipal principal, String id);

    Map<String, BigDecimal> processInterest(CustomPrincipal principal, String ruleId, Long numberOfDays, BigDecimal amount) throws ScriptException;


}
