package cm.g2s.partner.service.uaa.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {

    static final long serialVersionUID = 4001642415813445909L;

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String mobile;
    private String companyId;

}
