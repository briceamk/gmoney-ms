package cm.g2s.uaaservice.shared.mapper;

import cm.g2s.uaaservice.domain.model.User;
import cm.g2s.uaaservice.shared.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {
    User map(UserDto userDto);
    UserDto map(User user);
}
