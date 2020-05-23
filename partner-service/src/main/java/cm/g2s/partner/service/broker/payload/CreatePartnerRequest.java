package cm.g2s.partner.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartnerRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String mobile;
    private String companyId;
    private String userId;
}
