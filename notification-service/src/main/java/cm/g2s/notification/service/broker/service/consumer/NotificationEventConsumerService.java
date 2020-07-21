package cm.g2s.notification.service.broker.service.consumer;

import cm.g2s.notification.service.broker.payload.CreateSendMoneySuccessEmailRequest;
import cm.g2s.notification.service.broker.payload.JobRequest;

public interface NotificationEventConsumerService {

    void observeCreateEmailRequest(CreateSendMoneySuccessEmailRequest createSendMoneySuccessEmailRequest);

    void observeSendEmailRequest(JobRequest jobRequest);
}
