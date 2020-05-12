package cm.g2s.partner.shared.mapper;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.shared.dto.PartnerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateTimeMapper.class, DateMapper.class})
public interface PartnerMapper {
    Partner map(PartnerDto partnerDto);
    PartnerDto map(Partner partner);
}
