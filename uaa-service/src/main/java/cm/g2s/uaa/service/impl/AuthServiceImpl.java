package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.repository.UserRepository;
import cm.g2s.uaa.service.AuthService;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.shared.dto.RoleDto;
import cm.g2s.uaa.shared.payload.SignUp;
import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.exception.ConflictException;
import cm.g2s.uaa.shared.mapper.RoleMapper;
import cm.g2s.uaa.shared.mapper.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;

@Slf4j
@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    @Override
    public UserDto signUp(SignUp signUp) {
        // we check if username or email mobile is already used
        if (userRepository.existsByUsername(signUp.getUsername())) {
            log.warn("Username {} already used.", signUp.getUsername());
            throw new ConflictException(String.format("Username %s already used.", signUp.getUsername()));
        }

        if (userRepository.existsByEmail(signUp.getEmail())) {
            log.warn("Email {} already used.", signUp.getEmail());
            throw new ConflictException(String.format("Email %s already used.", signUp.getEmail()));
        }

        if (userRepository.existsByMobile(signUp.getMobile())) {
            log.warn("Mobile {} already used.", signUp.getMobile());
            throw new ConflictException(String.format("Mobile %s already used.", signUp.getMobile()));
        }

        // Creating user's account
        User user = new User();
        user.setFullName(signUp.getFullName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setMobile(signUp.getMobile());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        user = userRepository.save(user);
        //We disable user. user should enable account by clicking link send to his mailbox
        user.setEnabled(false);
        RoleDto roleDto = roleService.findByName(UaaConstantType.DEFAULT_USER_ROLE);
        user.setRoles(new LinkedHashSet<>(Arrays.asList(roleMapper.map(roleDto))));

        //TODO publish event USER_CREATED to partner-service and and notification-service
        return userMapper.map(userRepository.save(user));
    }
}
