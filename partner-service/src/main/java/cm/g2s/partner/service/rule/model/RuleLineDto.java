package cm.g2s.partner.service.rule.model;

import cm.g2s.partner.service.rule.model.RuleDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleLineDto {

    private String id;
    private String name;
    @NotEmpty(message = "operator is required")
    private String operator;
    @NotEmpty(message = "type is required")
    @Positive(message = "input is greater than zero")
    private Integer input;
    private String type;
    private Double amount;
    private Double factor;
    private String formula;
    @JsonIgnore
    private RuleDto ruleDto;
}