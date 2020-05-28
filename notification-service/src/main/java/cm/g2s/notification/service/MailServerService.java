package cm.g2s.notification.service;

import cm.g2s.notification.domain.model.MailServer;
import cm.g2s.notification.domain.model.MailServerType;
import cm.g2s.notification.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;


public interface MailServerService {
    MailServer create(CustomPrincipal principal, MailServer mailServer);

    void update(CustomPrincipal principal, MailServer mailServer);

    void testServer(CustomPrincipal principal, MailServer map);

    MailServer findById(CustomPrincipal principal, String id);

    Page<MailServer> findAll(CustomPrincipal principal, String hostname, PageRequest pageRequest);

    JavaMailSender getSenderServer(CustomPrincipal principal, MailServer mailServer);

    MailServer findByTypeAndDefaultServer(MailServerType out, boolean b);
}
