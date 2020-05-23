package cm.g2s.partner.web.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto implements Serializable {

    static final long serialVersionUID = -5729853946589879829L;

    private String id;
    @NotEmpty(message = "phone number is required")
    // TODO insert pattern or ORANGE or MTN phone number
    @Size(min = 9, max = 9, message = "Phone number length is exactly 9 digits.")
    private String name;
    @NotEmpty(message = "Type is required")
    private String type;
    private Boolean isDefault;
    private Boolean active;
    @JsonIgnore
    private PartnerDto partnerDto;

}
