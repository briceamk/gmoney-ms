package cm.g2s.uaa.security;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.repository.UserRepository;
import cm.g2s.uaa.security.commons.CustomPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service(value = "userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailOrMobile) throws UsernameNotFoundException {

        User user = userRepository.findByUsernameOrEmailOrMobile(usernameOrEmailOrMobile, usernameOrEmailOrMobile, usernameOrEmailOrMobile).orElseThrow(
                () -> {
                    log.error("User with login {} not found!", usernameOrEmailOrMobile);
                    throw new UsernameNotFoundException(String.format("User with login %s not found!", usernameOrEmailOrMobile));
                }
        );
        CustomPrincipal customPrincipal = CustomPrincipal.create(user);
        new AccountStatusUserDetailsChecker().check(customPrincipal);
        return customPrincipal;
    }
}
