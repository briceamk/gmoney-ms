package cm.g2s.uaa.shared.mapper;

import cm.g2s.uaa.domain.model.Role;
import cm.g2s.uaa.shared.dto.RoleDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper
@DecoratedWith(RoleMapperDecorator.class)
public interface RoleMapper {
    Role map(RoleDto roleDto);
    RoleDto map(Role role);
    Set<Role> mapToListEntity(Set<RoleDto> roleDtos);
    Set<RoleDto> mapToListDto(Set<Role> roles);
}
