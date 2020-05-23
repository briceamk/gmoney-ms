package cm.g2s.uaa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto implements Serializable {

    static final long serialVersionUID = -29757954562788404L;

    private String id;
    @NotEmpty(message = "name is required")
    private String name;
    private Set<PermissionDto> permissionDtos;
}
