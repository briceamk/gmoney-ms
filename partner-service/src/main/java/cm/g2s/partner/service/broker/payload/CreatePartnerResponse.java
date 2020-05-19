package cm.g2s.partner.service.broker.payload;

import cm.g2s.partner.service.uaa.UserDto;
import cm.g2s.partner.shared.dto.PartnerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartnerResponse {
    private UserDto userDto;
    private Boolean creationPartnerError;
    private PartnerDto partnerDto;
}
