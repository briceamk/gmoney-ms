package cm.g2s.uaa.shared.mapper;

import cm.g2s.uaa.domain.model.Role;
import cm.g2s.uaa.shared.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoleMapperDecorator implements RoleMapper{

    private  RoleMapper roleMapper;
    private  PermissionMapper permissionMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public Role map(RoleDto roleDto) {
        Role role = roleMapper.map(roleDto);
        role.setPermissions(permissionMapper.mapToListEntity(roleDto.getPermissionDtos()));
        return role;
    }

    @Override
    public RoleDto map(Role role) {
        RoleDto roleDto = roleMapper.map(role);
        roleDto.setPermissionDtos(permissionMapper.mapToListDto(role.getPermissions()));
        return roleDto;
    }

}
