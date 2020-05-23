package cm.g2s.account.web.dto;

import cm.g2s.account.service.partner.model.PartnerDto;
import cm.g2s.account.service.user.model.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements Serializable {

    static final long serialVersionUID = 4966500923313931917L;

    private String id;

    //@NotEmpty(message = "account number is required")
    //@Size(min = 10, max = 10, message= "account number should have exactly 10 characters")
    private String number;
    //@NotEmpty(message = "account number is required")
    //@Size(min = 2, max = 2, message= "account key  have exactly 2 characters")
    private String key;
    @NotNull(message = "balance can't not be null")
    private BigDecimal balance = new BigDecimal("00");
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", shape=JsonFormat.Shape.STRING)
    private LocalDateTime creationDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss", shape=JsonFormat.Shape.STRING)
    private LocalDateTime lastUpdateBalanceDate;
    private PartnerDto partnerDto;
    private UserDto userDto;
    private String state;

}
