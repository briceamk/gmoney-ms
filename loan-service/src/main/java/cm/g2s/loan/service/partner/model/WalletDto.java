package cm.g2s.loan.service.partner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    private String id;
    private String name;
    private String type;
    private Boolean active;
    @JsonIgnore
    private PartnerDto partnerDto;

}
