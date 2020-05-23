package cm.g2s.rule.web.mapper;

import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.web.dto.RuleDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RuleMapperDecorator implements RuleMapper{

    private RuleMapper ruleMapper;
    private RuleLineMapper ruleLineMapper;

    @Autowired
    public void setRuleMapper(RuleMapper ruleMapper) {
        this.ruleMapper = ruleMapper;
    }

    @Autowired
    public void setRuleLineMapper(RuleLineMapper ruleLineMapper) {
        this.ruleLineMapper = ruleLineMapper;
    }

    @Override
    public Rule map(RuleDto ruleDto) {
        Rule rule = ruleMapper.map(ruleDto);
        rule.setRuleLines(ruleLineMapper.mapListDto(ruleDto.getRuleLineDtos()));
        return rule;
    }

    @Override
    public RuleDto map(Rule rule) {
        RuleDto ruleDto = ruleMapper.map(rule);
        ruleDto.setRuleLineDtos(ruleLineMapper.mapListEntity(rule.getRuleLines()));
        return ruleDto;
    }
}
