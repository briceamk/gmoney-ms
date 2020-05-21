package cm.g2s.loan.service.partner.model;

import cm.g2s.loan.service.company.CompanyDto;
import cm.g2s.loan.service.rule.model.RuleDto;
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
public class PartnerDto {
    private String id;
    private String firstName;
    private String lastName;
    private BigDecimal creditLimit;
    private UserDto userDto;
    private RuleDto ruleDto;
    private PartnerCategoryDto categoryDto;
    private CompanyDto companyDto;
}
