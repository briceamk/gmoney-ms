package cm.g2s.partner.shared.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerCategoryDto {
    private String id;
    private Integer version;
    private String createdUid;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime lastModifiedDate;
    private String lastUpdatedUid;
    @NotEmpty(message = "name is required")
    //TODO add unique constraint validator
    private String name;
    @Positive(message = "Credit limit must greater than zero")
    private BigDecimal creditLimit;
    private Boolean active = true;
}
