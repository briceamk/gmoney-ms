package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.infrastructure.repository.UserRepository;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.exception.BadRequestException;
import cm.g2s.uaa.exception.ConflictException;
import cm.g2s.uaa.exception.ResourceNotFoundException;
import cm.g2s.uaa.web.payload.ResetPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        // we check if username or email mobile is already used
        if (userRepository.existsByUsername(user.getUsername())) {
            log.warn("Username {} already used.", user.getUsername());
            throw new ConflictException(String.format("Username %s already used.", user.getUsername()));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("Email {} already used.", user.getEmail());
            throw new ConflictException(String.format("Email %s already used.", user.getEmail()));
        }

        if (userRepository.existsByMobile(user.getMobile())) {
            log.warn("Mobile {} already used.", user.getMobile());
            throw new ConflictException(String.format("Mobile %s already used.", user.getMobile()));
        }

        return userRepository.save(user);
    }

    @Override
    public void update(User user) {

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            User dbUser = findById(user.getId());
            user.setPassword(dbUser.getPassword());
        }
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String userId, ResetPassword resetPassword) {
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
        update(user);
    }

    @Override
    public Page<User> findAll( String firstName, String lastName, String username, String email,
                               String mobile, PageRequest pageRequest) {

        Page<User> userPage;

        if (!StringUtils.isEmpty(firstName)) {
            //search by first name
            userPage = userRepository.findByFirstName(firstName, pageRequest);
        }
        else if(!StringUtils.isEmpty(lastName)) {
            //search by last name
            userPage = userRepository.findByLastName(lastName, pageRequest);
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

        return userPage;
    }

    @Override
    public User findById( String id) {
        return userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("User with id {} not found",id);
                    throw  new ResourceNotFoundException(String.format("User with id %s not found",id));
                }
        );
    }

    @Override
    public void deleteById(String id) {
        userRepository.delete(findById(id));
    }

}
