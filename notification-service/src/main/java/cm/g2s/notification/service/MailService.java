package cm.g2s.notification.service;

import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



public interface MailService {
    Mail create(CustomPrincipal principal, Mail mail);

    void update(CustomPrincipal principal, Mail mail);

    void send(CustomPrincipal principal, Mail mail);

    void sendAll(CustomPrincipal principal);

    Mail findById(CustomPrincipal principal, String id);

    Page<Mail> findAll(CustomPrincipal principal, String reference, String state, PageRequest pageRequest);

}
