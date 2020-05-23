package cm.g2s.partner.web.mapper;

import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.web.dto.PartnerCategoryDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class})
@DecoratedWith(PartnerCategoryMapperDecorator.class)
public interface PartnerCategoryMapper {
    PartnerCategory map(PartnerCategoryDto categoryDto);
    PartnerCategoryDto map(PartnerCategory category);
}
