package cm.g2s.loan.service.rule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleDto {

    private String id;
    @NotEmpty(message = "code is required")
    @Size(max = 8, message= "code can have maximum 8 characters")
    private String code;
    @NotEmpty(message = "name is required")
    @Size(max = 64, message= "name can have maximum 64 characters")
    private String name;
    private Boolean active = true;

}
