package cm.g2s.uaa.shared.dto;

import cm.g2s.uaa.service.partner.dto.PartnerDto;
import cm.g2s.uaa.service.company.model.CompanyDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    static final long serialVersionUID = -2404307862580487923L;

    private String id;
    @NotEmpty(message = "Full name is required")
    private String fullName;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;
    @NotEmpty(message = "Default phone number is required")
    private String mobile;
    @NotEmpty(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String city;
    private PartnerDto partnerDto;
    private CompanyDto companyDto;
    private String state;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
    private Set<RoleDto> roleDtos;
}
