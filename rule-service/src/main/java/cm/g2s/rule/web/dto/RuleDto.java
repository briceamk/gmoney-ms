package cm.g2s.rule.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto implements Serializable {

    static final long serialVersionUID = 2139369067072999412L;

    private String id;
    @NotEmpty(message = "code is required")
    @Size(max = 8, message= "code can have maximum 8 characters")
    private String code;
    @NotEmpty(message = "name is required")
    @Size(max = 64, message= "name can have maximum 64 characters")
    private String name;
    private Boolean active = true;
    @NotEmpty(message = "You must provide minimum one rule line!")
    private List<RuleLineDto> ruleLineDtos;

}
