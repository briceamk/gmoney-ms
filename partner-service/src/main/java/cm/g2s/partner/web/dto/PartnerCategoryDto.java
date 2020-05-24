package cm.g2s.partner.web.dto;

import cm.g2s.partner.service.rule.model.RuleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCategoryDto implements Serializable {

    static final long serialVersionUID = -5555252415629211011L;

    private String id;
    @NotEmpty(message = "name is required")
    //TODO add unique constraint validator
    private String name;
    @Positive(message = "Credit limit must greater than zero")
    private BigDecimal creditLimit;
    private Boolean active;
    private Boolean defaultCategory;
    @NotNull(message = "rule is required")
    private RuleDto ruleDto;

}
