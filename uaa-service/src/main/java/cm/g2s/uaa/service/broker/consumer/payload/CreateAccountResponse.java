package cm.g2s.uaa.service.broker.consumer.payload;

import cm.g2s.uaa.shared.dto.UserDto;
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
