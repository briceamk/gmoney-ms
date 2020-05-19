package cm.g2s.account.service.broker.payload;


import cm.g2s.account.service.user.model.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountResponse {
    private UserDto userDto;
    private Boolean creationAccountError;
}
