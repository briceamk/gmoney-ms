package cm.g2s.rule.service.impl;

import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.domain.model.RuleLine;
import cm.g2s.rule.repository.RuleRepository;
import cm.g2s.rule.security.CustomPrincipal;
import cm.g2s.rule.service.RuleService;
import cm.g2s.rule.exception.BadRequestException;
import cm.g2s.rule.exception.ConflictException;
import cm.g2s.rule.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service("ruleService")
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Rule create(CustomPrincipal principal, Rule rule) {
        //We check if rules exist
        if(ruleRepository.existsByCode(rule.getCode())) {
            log.error("provided code {} is already used!", rule.getCode());
            throw  new ConflictException(String.format("provided code %s is already used!", rule.getCode()));
        }
        rule.getRuleLines().forEach(ruleLine -> ruleLine.setRule(rule));
        rule.setActive(true);
        return ruleRepository.save(rule);
    }

    @Override
    public void update(CustomPrincipal principal, Rule rule) {
        //TODO validate unique fields
        rule.getRuleLines().forEach(ruleLine -> ruleLine.setRule(rule));
        ruleRepository.save(rule);
    }

    @Override
    public Rule findById(CustomPrincipal principal, String id) {
        return ruleRepository.findById(id).orElseThrow(
                () -> {
                    log.info("Rule with id {} not found", id);
                    throw new ResourceNotFoundException(String.format("Rule with id %s not found", id));
                }
        );

    }

    @Override
    public Page<Rule> findAll(CustomPrincipal principal, String code, String name, PageRequest pageRequest) {

        Page<Rule> rulePage;

        if (!StringUtils.isEmpty(code)) {
            //search by category name
            rulePage = ruleRepository.findByCode(code, pageRequest);
        } else if (!StringUtils.isEmpty(name)) {
            //search by partnerId
            rulePage = ruleRepository.findByName(name, pageRequest);
        }
        else{
            // search all
            rulePage = ruleRepository.findAll(pageRequest);
        }

        return rulePage;

    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        ruleRepository.delete(findById(principal, id));
    }

    @Override
    public Map<String, BigDecimal> processInterest(CustomPrincipal principal, String ruleId,
                                                   Long numberOfDays, BigDecimal amount) throws ScriptException {
        Rule rule = findById(principal, ruleId);
        Map<String, BigDecimal> result = new HashMap<>();
        if(rule.getRuleLines().size() <= 0) {
            log.error("No rule line for given rule!");
            throw new BadRequestException("No rule line for given rule!");
        }
        List<RuleLine>  ruleLines = rule.getRuleLines();
        for(RuleLine rl: ruleLines) {
            if(match(rl, numberOfDays)){
                BigDecimal interest = getAmount(rl, numberOfDays, amount).setScale(2, RoundingMode.HALF_UP);
                result.put("interest", interest);
                return result;
            }
        }
        result.put("interest", BigDecimal.ZERO);
        return result;
    }

    private Boolean match(RuleLine ruleLine, Long numberOfDays) {

        switch (ruleLine.getOperator()) {
            case EQUAL:
                return ruleLine.getInput() == numberOfDays;
            case LOWER_OR_EQUAL:
                return numberOfDays  <= ruleLine.getInput();
            case HIGHER_OR_EQUAL:
                return numberOfDays >= ruleLine.getInput();
            default:
                return false;
        }
    }

    private BigDecimal getAmount(RuleLine ruleLine, Long numberOfDays, BigDecimal amount ) throws ScriptException {
        @SuppressWarnings("removal")
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        switch (ruleLine.getType()) {
            case FACTOR:
                return amount.multiply(new BigDecimal(ruleLine.getFactor()/100));
            case FORMULA:
                return new BigDecimal(engine.eval(ruleLine.getFormula().replace("numberOfDays", numberOfDays.toString()).replace("amount", amount.toPlainString())).toString()); // Implement when we get value
            case CONSTANT:
                return new BigDecimal(ruleLine.getAmount());
            default:
                return BigDecimal.ZERO;
        }
    }
}
