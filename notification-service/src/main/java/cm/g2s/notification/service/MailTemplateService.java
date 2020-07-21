package cm.g2s.notification.service;

import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.domain.model.MailTemplateType;
import cm.g2s.notification.security.CustomPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface MailTemplateService {
    MailTemplate create(CustomPrincipal principal, MailTemplate mailTemplate);

    void update(CustomPrincipal principal, MailTemplate mailTemplate);

    MailTemplate findById(CustomPrincipal principal, String id);

    MailTemplate findByName(CustomPrincipal principal, String name);

    MailTemplate findByType(CustomPrincipal principal, MailTemplateType type);

    Page<MailTemplate> findAll(CustomPrincipal principal, String name, PageRequest pageRequest);
}
