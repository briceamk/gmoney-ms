package cm.g2s.uaaservice.shared.mapper;

import cm.g2s.uaaservice.domain.model.User;
import cm.g2s.uaaservice.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserMapperDecorator implements UserMapper{

    private  UserMapper userMapper;
    private  RoleMapper roleMapper;

    @Autowired
    public UserMapper getUserMapper() {
        return userMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public User map(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setRoles(roleMapper.mapToListEntity(userDto.getRoleDtos()));
        return user;
    }

    @Override
    public UserDto map(User user) {
        UserDto userDto = userMapper.map(user);
        userDto.setRoleDtos(roleMapper.mapToListDto(user.getRoles()));
        return userDto;
    }
}
