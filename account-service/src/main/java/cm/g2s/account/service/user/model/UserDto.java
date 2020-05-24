package cm.g2s.account.service.user.model;


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
    private String email;
    private String mobile;
}
