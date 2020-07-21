package cm.g2s.notification.service.broker.service.publisher;


import cm.g2s.notification.service.broker.payload.CreateSendMoneySuccessEmailResponse;

public interface NotificationEventPublisherService {

    void onCreateSendMoneySuccessEmailResponseEvent
            (CreateSendMoneySuccessEmailResponse createSendMoneySuccessEmailResponse);
}
