package cm.g2s.partner.service.uaa.service;


import cm.g2s.partner.service.uaa.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service("uaaClientService")
public class UaaClientServiceImpl implements UaaClientService {

    private final UaaClientService uaaClientService;

    @Override
    public UserDto findById(String id) {
        log.info("Calling uaa-service api from partner-service.....");
        return uaaClientService.findById(id);
    }

}
