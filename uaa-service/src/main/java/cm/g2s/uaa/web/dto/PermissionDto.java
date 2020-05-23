package cm.g2s.uaa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto implements Serializable {

    static final long serialVersionUID = -4883725038126150242L;

    private String id;
    @NotEmpty(message = "name is required")
    private String name;

}
