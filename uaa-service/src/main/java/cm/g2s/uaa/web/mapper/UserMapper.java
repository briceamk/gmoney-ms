package cm.g2s.uaa.web.mapper;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.web.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {
    User map(UserDto userDto);
    UserDto map(User user);
}
