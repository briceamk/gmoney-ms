package cm.g2s.account.service.user.service;

import cm.g2s.account.service.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service("userClientService")
public class UserClientServiceImpl implements UserClientService {

    private final UserClientService userClientService;

    @Override
    public UserDto findById(String id) {
        return userClientService.findById(id);
    }
}
