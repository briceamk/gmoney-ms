package cm.g2s.notification.service.impl;

import cm.g2s.notification.constant.NotificationConstantType;
import cm.g2s.notification.domain.model.*;
import cm.g2s.notification.exception.ResourceNotFoundException;
import cm.g2s.notification.infrastructure.repository.MailRepository;
import cm.g2s.notification.security.CustomPrincipal;
import cm.g2s.notification.service.MailServerService;
import cm.g2s.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("mailService")
public class MailServiceImpl implements MailService {

    private final MailRepository mailRepository;
    private final MailServerService mailServerService;


    @Override
    public Mail create(CustomPrincipal principal, Mail mail) {
        if(mail.getState() == null)
            mail.setState(MailState.TO_SEND);
        return mailRepository.save(mail);
    }

    @Override
    public void update(CustomPrincipal principal, Mail mail) {
        mailRepository.save(mail);
    }

    @Override
    public void send(CustomPrincipal principal, Mail mail) {
        // we first check the default mail server
        MailServer mailServer = mailServerService.findByTypeAndDefaultServer(MailServerType.OUT, true);

        JavaMailSender mailSender = mailServerService.getSenderServer(principal, mailServer);

        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);

            mimeMessageHelper.setFrom(mailServer.getUsername());

            mimeMessageHelper.setTo(mail.getEmailTo().split(NotificationConstantType.MAIL_SEPARATOR));

            mimeMessageHelper.setSubject(mail.getSubject());

            mimeMessageHelper.setText(mail.getContent(), true);

            if(mail.getEmailCc() != null && !mail.getEmailCc().isEmpty())
                mimeMessageHelper.setBcc(mail.getEmailCc().split(NotificationConstantType.MAIL_SEPARATOR));

            if(mail.getEmailCci() != null && !mail.getEmailCci().isEmpty())
                mimeMessageHelper.setCc(mail.getEmailCci().split(NotificationConstantType.MAIL_SEPARATOR));
            mailSender.send(message);
            mailServer.setState(MailServerState.ACTIVE);
            mail.setState(MailState.SEND);
            mail.setSendDate(Timestamp.valueOf(LocalDateTime.now()));

        } catch (MessagingException e) {
            log.error("error when sending the mail. invalid parameter!, {}", e.getMessage());
            mail.setState(MailState.SEND_EXCEPTION);
        }
        finally {
            update(principal, mail);
        }
    }

    @Override
    public void sendAll(CustomPrincipal principal) {
        List<Mail> mails = mailRepository.findByStateNot(MailState.SEND);

        if (mails != null) {
            mails.forEach(mail -> {
                send(principal, mail);
            });
        }
    }

    @Override
    public Mail findById(CustomPrincipal principal, String id) {
        return mailRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Mail with id {} not found!", id);
                    throw new ResourceNotFoundException(String.format("Mail with id %s not found!", id));
                }
        );
    }

    @Override
    public Page<Mail> findAll(CustomPrincipal principal, String reference, String state, PageRequest pageRequest) {

        Page<Mail> mailPage;

        if (!StringUtils.isEmpty(reference)) {
            //search by reference
            mailPage = mailRepository.findByReference(reference, pageRequest);
        } else if (!StringUtils.isEmpty(state)) {
            //search by state
            mailPage = mailRepository.findByState(MailState.valueOf(state), pageRequest);
        }
        else{
            // search all
            mailPage = mailRepository.findAll(pageRequest);
        }

        return mailPage;
    }

    @Override
    public List<Mail> findByStateNot(MailState send) {
        return mailRepository.findByStateNot(MailState.SEND);
    }

}
