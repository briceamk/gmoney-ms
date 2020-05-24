package cm.g2s.account.service.partner.model;

import cm.g2s.account.service.user.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private String id;
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    private UserDto userDto;
}
