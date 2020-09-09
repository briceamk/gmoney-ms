package cm.g2s.loan.web.dto;


import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.domain.model.LoanState;
import cm.g2s.loan.service.account.model.AccountDto;
import cm.g2s.loan.service.company.CompanyDto;
import cm.g2s.loan.service.partner.model.PartnerDto;
import cm.g2s.loan.service.rule.model.RuleDto;
import cm.g2s.loan.service.user.UserDto;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanDto {

    private String id;
    private String number;
    private String fullName;
    private String email;
    @NotEmpty(message = "mobile number is require")
    private String mobile;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;
    @NotNull(message = "issue date is required")
    @Future(message = "issue date must be future date")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime issueDate;
    @NotNull(message = "amount is required")
    @Positive(message = "amount must be greater than zeros")
    private BigDecimal amount;
    private BigDecimal interest;
    @NotEmpty(message = "payment mode is required")
    private String mode;
    private String state = LoanState.DRAFT.name();
    private RuleDto ruleDto;
    private PartnerDto partnerDto;
    private AccountDto accountDto;
    private CompanyDto companyDto;
    private UserDto userDto;
}
