package cm.g2s.partner.shared.mapper;

import cm.g2s.partner.domain.model.PartnerCategory;
import cm.g2s.partner.shared.dto.PartnerCategoryDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class})
@DecoratedWith(PartnerCategoryMapperDecorator.class)
public interface PartnerCategoryMapper {
    PartnerCategory map(PartnerCategoryDto categoryDto);
    PartnerCategoryDto map(PartnerCategory category);
}
