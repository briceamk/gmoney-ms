package cm.g2s.rule.service;

import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;

public interface RuleService {
    Rule create(CustomPrincipal principal, Rule rule);

    void update(CustomPrincipal principal, Rule rule);

    Rule findById(CustomPrincipal principal, String id);

    Page<Rule> findAll(CustomPrincipal principal, String code, String name, PageRequest of);

    void deleteById(CustomPrincipal principal, String id);

    Map<String, BigDecimal> processInterest(CustomPrincipal principal, String ruleId, Long numberOfDays, BigDecimal amount) throws ScriptException;


}
