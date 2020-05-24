package cm.g2s.loan.service.account.model;

import cm.g2s.loan.service.partner.model.PartnerDto;
import cm.g2s.loan.service.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String id;
    private String number;
    private String key;
    private String state;
    private BigDecimal balance;
}
