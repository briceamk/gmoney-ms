package cm.g2s.uaaservice.service.impl;

import cm.g2s.uaaservice.constant.UaaConstantType;
import cm.g2s.uaaservice.domain.model.Role;
import cm.g2s.uaaservice.domain.model.User;
import cm.g2s.uaaservice.repository.UserRepository;
import cm.g2s.uaaservice.service.AuthService;
import cm.g2s.uaaservice.service.RoleService;
import cm.g2s.uaaservice.shared.dto.RoleDto;
import cm.g2s.uaaservice.shared.dto.SignUpDto;
import cm.g2s.uaaservice.shared.dto.UserDto;
import cm.g2s.uaaservice.shared.exception.ConflictException;
import cm.g2s.uaaservice.shared.mapper.RoleMapper;
import cm.g2s.uaaservice.shared.mapper.UserMapper;

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
    public UserDto signUp(SignUpDto signUp) {
        // we check if username or email mobile is already used
        if (userRepository.existsByUsername(signUp.getUsername())) {
            log.warn("Username {} already used.", signUp.getUsername());
            throw new ConflictException(String.format("Username %s already used.", signUp.getUsername()));
        }

        if (userRepository.existsByUsername(signUp.getEmail())) {
            log.warn("Email {} already used.", signUp.getEmail());
            throw new ConflictException(String.format("Email %s already used.", signUp.getEmail()));
        }

        if (userRepository.existsByUsername(signUp.getMobile())) {
            log.warn("Mobile {} already used.", signUp.getMobile());
            throw new ConflictException(String.format("Mobile %s already used.", signUp.getMobile()));
        }

        // Creating user's account
        User user = new User();
        user.setFullName(signUp.getFullName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setMobile(signUp.getMobile());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        RoleDto roleDto = roleService.findByName(UaaConstantType.DEFAULT_USER_ROLE);
        user.setRoles(new LinkedHashSet<>(Arrays.asList(roleMapper.map(roleDto))));

        // we disable user. user should enable account by clicking link send to his mailbox
        user.setEnabled(false);
        //TODO publish event USER_CREATED to partner-service
        return userMapper.map(userRepository.save(user));
    }
}
