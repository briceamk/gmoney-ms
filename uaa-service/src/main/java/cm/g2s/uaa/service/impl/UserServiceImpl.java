package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.repository.UserRepository;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.dto.UserDtoPage;
import cm.g2s.uaa.shared.exception.BadRequestException;
import cm.g2s.uaa.shared.exception.ConflictException;
import cm.g2s.uaa.shared.exception.ResourceNotFoundException;
import cm.g2s.uaa.shared.mapper.UserMapper;
import cm.g2s.uaa.shared.payload.ResetPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.nio.file.attribute.UserPrincipal;
import java.util.stream.Collectors;


@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto create(UserPrincipal userPrincipal, UserDto userDto) {
        // we check if username or email mobile is already used
        if (userRepository.existsByUsername(userDto.getUsername())) {
            log.warn("Username {} already used.", userDto.getUsername());
            throw new ConflictException(String.format("Username %s already used.", userDto.getUsername()));
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.warn("Email {} already used.", userDto.getEmail());
            throw new ConflictException(String.format("Email %s already used.", userDto.getEmail()));
        }

        if (userRepository.existsByMobile(userDto.getMobile())) {
            log.warn("Mobile {} already used.", userDto.getMobile());
            throw new ConflictException(String.format("Mobile %s already used.", userDto.getMobile()));
        }

        // Creating user's account
        User user = userMapper.map(userDto);

        //TODO publish event USER_CREATED to partner-service and notification-service
        return userMapper.map(userRepository.save(user));
    }

    @Override
    public void update(UserPrincipal userPrincipal, UserDto userDto) {

        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            UserDto dbUserDto = findById(userPrincipal, userDto.getId());
            userDto.setPassword(dbUserDto.getPassword());
        }
        userRepository.save(userMapper.map(userDto));
    }

    @Override
    public void resetPassword(UserPrincipal userPrincipal, String userId, ResetPassword resetPassword) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.error("User with id {} not found",userId);
                    throw  new ResourceNotFoundException(String.format("User with id %s not found",userId));
                }
        );
        if(!passwordEncoder.matches(resetPassword.getOldPassword(), user.getPassword())) {
            log.warn("your old password does't not matches!");
            throw new BadRequestException("your old password does't not matches!");
        }
        user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
        update(userPrincipal, userMapper.map(user));
    }

    @Override
    public UserDtoPage findAll(UserPrincipal userPrincipal, String fullName, String username, String email, String mobile, PageRequest pageRequest) {

        Page<User> userPage;

        if (!StringUtils.isEmpty(fullName)) {
            //search by full name
            userPage = userRepository.findByFullName(fullName, pageRequest);
        }
        else if(!StringUtils.isEmpty(username)) {
            //search by username
            userPage = userRepository.findByUsername(username, pageRequest);
        }
        else if(!StringUtils.isEmpty(email)) {
            //search by username
            userPage = userRepository.findByEmail(email, pageRequest);
        }
        else if(!StringUtils.isEmpty(mobile)) {
            //search by username
            userPage = userRepository.findByMobile(mobile, pageRequest);
        }
        else{
            // search all
            userPage = userRepository.findAll(pageRequest);
        }

        return new UserDtoPage(
                userPage.getContent().stream().map(userMapper::map).collect(Collectors.toList()),
                PageRequest.of(userPage.getPageable().getPageNumber(),
                        userPage.getPageable().getPageSize()),
                userPage.getTotalElements()
        );
    }

    @Override
    public UserDto findById(UserPrincipal userPrincipal, String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("User with id {} not found",id);
                    throw  new ResourceNotFoundException(String.format("User with id %s not found",id));
                }
        );
        return userMapper.map(user);
    }

    @Override
    public void deleteById(UserPrincipal userPrincipal, String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("User with id {} not found",id);
                    throw  new ResourceNotFoundException(String.format("User with id %s not found",id));
                }
        );
        userRepository.delete(user);
    }

}
