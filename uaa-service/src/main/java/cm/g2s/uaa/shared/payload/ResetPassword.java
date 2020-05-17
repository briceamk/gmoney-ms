package cm.g2s.uaa.shared.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassword {
    @NotEmpty(message = "old password is required!")
    private String oldPassword;
    @NotEmpty(message = "new password is required")
    @Size(message = "minimal 6 characters is required!", min = 6)
    private String newPassword;
}
