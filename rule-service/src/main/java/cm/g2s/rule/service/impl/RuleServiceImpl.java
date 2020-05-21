package cm.g2s.rule.service.impl;

import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.domain.model.RuleLine;
import cm.g2s.rule.repository.RuleRepository;
import cm.g2s.rule.security.CustomPrincipal;
import cm.g2s.rule.service.RuleService;
import cm.g2s.rule.shared.dto.RuleDto;
import cm.g2s.rule.shared.dto.RuleDtoPage;
import cm.g2s.rule.shared.exception.BadRequestException;
import cm.g2s.rule.shared.exception.ConflictException;
import cm.g2s.rule.shared.exception.ResourceNotFoundException;
import cm.g2s.rule.shared.mapper.RuleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("ruleService")
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;
    private final RuleMapper ruleMapper;

    @Override
    public RuleDto create(CustomPrincipal principal, RuleDto ruleDto) {
        //We check if rules exist
        if(ruleRepository.existsByCode(ruleDto.getCode())) {
            log.error("provided code {} is already used!", ruleDto.getCode());
            throw  new ConflictException(String.format("provided code %s is already used!", ruleDto.getCode()));
        }
        val rule = ruleMapper.map(ruleDto);
        rule.getRuleLines().forEach(ruleLine -> ruleLine.setRule(rule));
        rule.setActive(true);
        return ruleMapper.map(ruleRepository.save(rule));
    }

    @Override
    public void update(CustomPrincipal principal, RuleDto ruleDto) {
        //TODO validate unique fields
        val rule = ruleMapper.map(ruleDto);
        rule.getRuleLines().forEach(ruleLine -> ruleLine.setRule(rule));
        ruleRepository.save(rule);
    }

    @Override
    public RuleDto findById(CustomPrincipal principal, String id) {
        val rule = ruleRepository.findById(id).orElseThrow(
                () -> {
                    log.info("Rule with id {} not found", id);
                    throw new ResourceNotFoundException(String.format("Rule with id %s not found", id));
                }
        );
        return ruleMapper.map(rule);
    }

    @Override
    public RuleDtoPage findAll(CustomPrincipal principal, String code, String name, PageRequest pageRequest) {

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

        return new RuleDtoPage(
                rulePage.getContent().stream().map(ruleMapper::map).collect(Collectors.toList()),
                PageRequest.of(rulePage.getPageable().getPageNumber(),
                        rulePage.getPageable().getPageSize()),
                rulePage.getTotalElements()
        );

    }

    @Override
    public void deleteById(CustomPrincipal principal, String id) {
        val rule = ruleRepository.findById(id).orElseThrow(
                () -> {
                    log.info("Rule with id {} not found", id);
                    throw new ResourceNotFoundException(String.format("Rule with id %s not found", id));
                }
        );
        ruleRepository.delete(rule);
    }

    @Override
    public Map<String, BigDecimal> processInterest(CustomPrincipal principal, String ruleId,
                                                   Long numberOfDays, BigDecimal amount) throws ScriptException {
        Rule rule = ruleMapper.map(findById(principal, ruleId));
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
