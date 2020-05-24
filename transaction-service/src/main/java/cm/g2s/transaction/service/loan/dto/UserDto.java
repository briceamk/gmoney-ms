package cm.g2s.transaction.service.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String mobile;
    private String email;
}
