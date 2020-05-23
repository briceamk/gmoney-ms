package cm.g2s.rule.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleLineDto implements Serializable {

    static final long serialVersionUID = 8745353155335729757L;

    private String id;
    private String name;
    @NotEmpty(message = "operator is required")
    private String operator;
    @NotEmpty(message = "type is required")
    @Positive(message = "input is greater than zero")
    private Long input;
    private String type;
    private Double amount;
    private Double factor;
    private String formula;
    @JsonIgnore
    private RuleDto ruleDto;

}
