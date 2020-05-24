package cm.g2s.uaa.web.dto;


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
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
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
    private String  partnerId;
    private String  companyId;
    private String state;
    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean credentialsNonExpired = true;
    private Boolean enabled = true;
    private Set<RoleDto> roleDtos;
}
