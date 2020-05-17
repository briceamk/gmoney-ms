package cm.g2s.uaa.shared.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUp implements Serializable {

    static final long serialVersionUID = -3358696467111165412L;

    @NotEmpty(message = "full name is required!")
    @Size(message = "full name must have maximum 128 characters!", max = 128)
    private String fullName;
    @NotEmpty(message = "username is required!")
    @Size(message = "username must have maximum 32 characters!", max = 32)
    private String username;
    @NotEmpty(message = "email is required!")
    @Email(message = "invalid email!")
    @Size(message = "email must have maximum 32 characters", max = 128)
    private String email;
    @NotEmpty(message = "mobile is required")
    //TODO validate number format
    private String mobile;
    @NotEmpty(message = "city is required")
    private String city;
    @NotEmpty(message = "password is required")
    @Size(message = "password must have between 6 and 32 characters", min = 6, max = 32)
    private String password;
}
