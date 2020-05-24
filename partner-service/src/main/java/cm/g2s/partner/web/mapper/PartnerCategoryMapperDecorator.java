package cm.g2s.partner.web.mapper;


import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.service.rule.model.RuleDto;
import cm.g2s.partner.service.rule.service.RuleClientService;
import cm.g2s.partner.web.dto.PartnerCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class PartnerCategoryMapperDecorator implements PartnerCategoryMapper{

    private RuleClientService ruleClientService;
    private PartnerCategoryMapper categoryMapper;

    @Autowired
    public void setRuleClientService(RuleClientService ruleClientService) {
        this.ruleClientService = ruleClientService;
    }

    @Autowired
    public void setCategoryMapper(PartnerCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public PartnerCategory map(PartnerCategoryDto categoryDto) {
        PartnerCategory category = categoryMapper.map(categoryDto);
        if(categoryDto.getRuleDto() != null)
            category.setRuleId(categoryDto.getRuleDto().getId());
        return category;
    }

    @Override
    public PartnerCategoryDto map(PartnerCategory category) {
        PartnerCategoryDto categoryDto = categoryMapper.map(category);
        if(category.getRuleId() != null ) {
            RuleDto ruleDto = ruleClientService.findById(category.getRuleId());
            if(ruleDto != null)
                categoryDto.setRuleDto(ruleDto);
        }
        return categoryDto;
    }
}
