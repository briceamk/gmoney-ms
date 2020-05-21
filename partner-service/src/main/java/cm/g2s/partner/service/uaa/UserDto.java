package cm.g2s.partner.service.uaa;

import cm.g2s.partner.service.company.model.CompanyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    static final long serialVersionUID = 4001642415813445909L;

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
    @NotEmpty(message = "city is required")
    private String city;
    private CompanyDto companyDto;



}
