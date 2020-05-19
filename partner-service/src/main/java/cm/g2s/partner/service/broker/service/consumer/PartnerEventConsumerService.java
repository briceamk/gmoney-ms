package cm.g2s.partner.service.broker.service.consumer;

import cm.g2s.partner.service.uaa.UserDto;

public interface PartnerEventConsumerService {
    void observePartnerCreateRequest(UserDto userDto);
    void observeAccountCreateFailed(UserDto userDto);
}
