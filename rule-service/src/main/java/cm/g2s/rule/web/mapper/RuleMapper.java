package cm.g2s.rule.web.mapper;

import cm.g2s.rule.domain.model.Rule;
import cm.g2s.rule.web.dto.RuleDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
@DecoratedWith(RuleMapperDecorator.class)
public interface RuleMapper {
    Rule map(RuleDto ruleDto);
    RuleDto map(Rule rule);
}
