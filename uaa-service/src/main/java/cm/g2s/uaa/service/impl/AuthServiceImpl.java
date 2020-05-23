package cm.g2s.uaa.service.impl;


import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.model.Role;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.infrastructure.repository.UserRepository;
import cm.g2s.uaa.service.AuthService;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.service.company.model.CompanyDto;
import cm.g2s.uaa.service.company.service.CompanyClientService;
import cm.g2s.uaa.exception.ConflictException;
import cm.g2s.uaa.exception.ResourceNotFoundException;
import cm.g2s.uaa.web.payload.SignUp;
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
    private final CompanyClientService companyClientService;
    private final RoleService roleService;

    @Override
    public User signUp(SignUp signUp) {

        // we check if username or email mobile is already used
        if (userRepository.existsByUsername(signUp.getUsername())) {
            log.error("Username {} already used.", signUp.getUsername());
            throw new ConflictException(String.format("Username %s already used.", signUp.getUsername()));
        }

        if (userRepository.existsByEmail(signUp.getEmail())) {
            log.error("Email {} already used.", signUp.getEmail());
            throw new ConflictException(String.format("Email %s already used.", signUp.getEmail()));
        }

        if (userRepository.existsByMobile(signUp.getMobile())) {
            log.error("Mobile {} already used.", signUp.getMobile());
            throw new ConflictException(String.format("Mobile %s already used.", signUp.getMobile()));
        }

        // Creating user's account
        User user = new User();
        user.setFirstName(signUp.getFirstName() != null? signUp.getFirstName() :  "");
        user.setLastName(signUp.getLastName());
        user.setUsername(signUp.getUsername());
        user.setEmail(signUp.getEmail());
        user.setMobile(signUp.getMobile());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setCity(signUp.getCity());
        user.setPassword(passwordEncoder.encode(signUp.getPassword()));
        //Default sate
        user.setState(UserState.NEW);
        //We disable user. user should enable account by clicking link send to his mailbox
        user.setEnabled(false);
        //We check company if user has send company code
        if(signUp.getCompanyCode() != null && !signUp.getCompanyCode().isEmpty()) {
            CompanyDto companyDto = companyClientService.findByCode(signUp.getCompanyCode());
            if(companyDto.getId() == null) {
                log.error("Company with provided code {} not found", signUp.getCompanyCode());
                throw new ResourceNotFoundException(String.format("Company with provided code {} not found", signUp.getCompanyCode()));
            }
            user.setCompanyId(companyDto.getId());
        }
        user = userRepository.save(user);
        Role role = roleService.findByName(UaaConstantType.DEFAULT_USER_ROLE);
        user.setRoles(new LinkedHashSet<>(Arrays.asList(role)));
        return userRepository.save(user);
    }
}
