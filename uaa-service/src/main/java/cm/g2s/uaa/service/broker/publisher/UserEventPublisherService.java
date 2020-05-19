package cm.g2s.uaa.service.broker.publisher;

import cm.g2s.uaa.shared.dto.UserDto;

public interface UserEventPublisherService {
    void onCreatePartnerEvent(UserDto userDto);

    void onCreateAccountEvent(UserDto userDto);

    void onCreateAccountFailedEvent(UserDto userDto);
}
