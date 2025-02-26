package cm.g2s.uaa.web.mapper;

import cm.g2s.uaa.domain.model.Permission;
import cm.g2s.uaa.web.dto.PermissionDto;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
public interface PermissionMapper {
    Permission map(PermissionDto permissionDto);
    PermissionDto map(Permission permission);
    Set<Permission> mapToListEntity(Set<PermissionDto> permissionDtos);
    Set<PermissionDto> mapToListDto(Set<Permission> permissions);
}
