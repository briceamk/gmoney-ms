package cm.g2s.rule.shared.mapper;

import cm.g2s.rule.domain.model.RuleLine;
import cm.g2s.rule.shared.dto.RuleLineDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
public interface RuleLineMapper {
    RuleLine map(RuleLineDto ruleLineDto);
    RuleLineDto map(RuleLine ruleLine);
    List<RuleLine> mapListDto(List<RuleLineDto> ruleLineDtos);
    List<RuleLineDto> mapListEntity(List<RuleLine> ruleLines);
}
