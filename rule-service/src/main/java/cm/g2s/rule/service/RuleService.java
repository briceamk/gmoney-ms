package cm.g2s.rule.service;

import cm.g2s.rule.shared.dto.RuleDto;
import cm.g2s.rule.shared.dto.RuleDtoPage;
import org.springframework.data.domain.PageRequest;

import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.Map;

public interface RuleService {
    RuleDto create(RuleDto ruleDto);

    void update(RuleDto ruleDto);

    RuleDto findById(String id);

    RuleDtoPage findAll(String code, String name, PageRequest of);

    void deleteById(String id);

    Map<String, BigDecimal> getRuleLineValue(String id, Integer numberOfDays, BigDecimal amount) throws ScriptException;


}
