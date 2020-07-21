package cm.g2s.notification.service.broker.service.consumer.impl;

import cm.g2s.notification.constant.NotificationConstantType;
import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.domain.model.MailTemplateType;
import cm.g2s.notification.service.MailService;
import cm.g2s.notification.service.MailTemplateService;
import cm.g2s.notification.service.broker.payload.*;
import cm.g2s.notification.service.broker.service.consumer.NotificationEventConsumerService;
import cm.g2s.notification.service.broker.service.publisher.NotificationEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service("notificationEventConsumerService")
public class NotificationEventConsumerServiceImpl implements NotificationEventConsumerService {

    private final MailTemplateService mailTemplateService;
    private final MailService mailService;
    private final NotificationEventPublisherService publisherService;

    @Override
    @StreamListener(target = "loanCreateSendMoneySuccessEmailChannel", condition = "headers['loan'] == 'createSendMoneySuccessEmail'")
    public void observeCreateEmailRequest(@Payload CreateSendMoneySuccessEmailRequest createSendMoneySuccessEmailRequest) {
        log.info("Receiving Send Money Notification Success from loan-service");
        CreateSendMoneySuccessEmailResponse.CreateSendMoneySuccessEmailResponseBuilder builder = CreateSendMoneySuccessEmailResponse.builder();
        try {
            MailTemplate mailTemplate = mailTemplateService.findByType(null, MailTemplateType.valueOf(createSendMoneySuccessEmailRequest.getType()));
            mailTemplate.setContent(
                    mailTemplate.getContent()
                        .replace(NotificationConstantType.SEND_MONEY_SUCCESS_LOAN_NUMBER, createSendMoneySuccessEmailRequest.getLoanNumber())
                        .replace(NotificationConstantType.SEND_MONEY_SUCCESS_AMOUNT, createSendMoneySuccessEmailRequest.getAmount().toPlainString())
                        .replace(NotificationConstantType.SEND_MONEY_SUCCESS_BALANCE, createSendMoneySuccessEmailRequest.getBalance().toPlainString())
                        .replace(NotificationConstantType.SEND_MONEY_SUCCESS_FULL_NAME, createSendMoneySuccessEmailRequest.getFullName())
            );
            mailTemplate.setSubject(
                    mailTemplate.getSubject().replace(NotificationConstantType.SEND_MONEY_SUCCESS_LOAN_NUMBER, createSendMoneySuccessEmailRequest.getLoanNumber())
            );
            Mail mail = Mail.builder()
                    .subject(mailTemplate.getSubject())
                    .content(mailTemplate.getContent())
                    .emailTo(createSendMoneySuccessEmailRequest.getEmail())
                    .relatedObjectId(createSendMoneySuccessEmailRequest.getLoanId())
                    .relatedClass(NotificationConstantType.LOAN_CLASS)
                    .reference(createSendMoneySuccessEmailRequest.getLoanNumber())
                    .build();
            mailService.create(null, mail);
            builder
                    .createSendMoneySuccessEmailError(false)
                    .loanId(createSendMoneySuccessEmailRequest.getLoanId());
            log.info("Send Money Success Notification Successfully");
        } catch (Exception e) {
            builder
                    .createSendMoneySuccessEmailError(true)
                    .loanId(createSendMoneySuccessEmailRequest.getLoanId());
            log.info("Error when sending Money Success Notification");
        } finally {
            publisherService.onCreateSendMoneySuccessEmailResponseEvent(builder.build());
        }
    }

    @Override
    @StreamListener(value = "cronSendEmailChannel", condition = "headers['cron'] == 'sendEmail'")
    public void observeSendEmailRequest(@Payload JobRequest jobRequest) {
        log.info("Receiving send email request from cron-service");
        if(jobRequest.getEventType().equals(JobType.SEND_EMAIL)) {
            mailService.sendAll(null);
        }

    }
}
