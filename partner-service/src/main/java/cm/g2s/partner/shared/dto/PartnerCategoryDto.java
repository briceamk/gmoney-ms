package cm.g2s.partner.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCategoryDto implements Serializable {

    static final long serialVersionUID = 7205702359578891281L;

    private String id;
    @NotEmpty(message = "name is required")
    //TODO add unique constraint validator
    private String name;
    @Positive(message = "Credit limit must greater than zero")
    private BigDecimal creditLimit;
    private Boolean active = true;

}
