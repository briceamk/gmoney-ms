package cm.g2s.partner.shared.dto;

import cm.g2s.partner.domain.model.Partner;
import cm.g2s.partner.domain.model.WalletType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private String id;
    private Integer version;
    private String createdUid;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime lastModifiedDate;
    private String lastUpdatedUid;
    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "", message = "you should give a valid, for your chosen type")
    // TODO insert pattern or ORANGE or MTN phone number
    @Size(min = 9, max = 9, message = "Phone number length is exactly 9 digits.")
    private String name;
    @NotEmpty(message = "Type is required")
    private WalletType type;
    private Boolean isDefault;
    private Boolean active = true;
    @JsonIgnore
    private Partner partner;
}
