package cm.g2s.uaa.web.mapper;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class UserMapperDecorator implements UserMapper{

    private  UserMapper userMapper;
    private  RoleMapper roleMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }


    @Override
    public User map(UserDto userDto) {
        User user = userMapper.map(userDto);
        if(userDto.getRoleDtos() != null)
            user.setRoles(roleMapper.mapToListEntity(userDto.getRoleDtos()));
        if(userDto.getState() != null && !userDto.getState().isEmpty())
            user.setState(UserState.valueOf(userDto.getState()));
        return user;
    }

    @Override
    public UserDto map(User user) {
        UserDto userDto = userMapper.map(user);
        if(user.getRoles() != null )
            userDto.setRoleDtos(roleMapper.mapToListDto(user.getRoles()));
        if(user.getState() != null)
            userDto.setState(user.getState().name());
        return userDto;
    }
}
