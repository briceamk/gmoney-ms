package cm.g2s.notification.service.impl;

import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.domain.model.MailState;
import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.exception.ConflictException;
import cm.g2s.notification.exception.ResourceNotFoundException;
import cm.g2s.notification.infrastructure.repository.MailTemplateRepository;
import cm.g2s.notification.security.CustomPrincipal;
import cm.g2s.notification.service.MailTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@RequiredArgsConstructor
@Service("mailTemplateService")
public class MailTemplateServiceImpl implements MailTemplateService {

    private final MailTemplateRepository mailTemplateRepository;

    @Override
    public MailTemplate create(CustomPrincipal principal, MailTemplate mailTemplate) {
        if(mailTemplateRepository.existsByName(mailTemplate.getName())) {
            log.error("A template with name {} exists!", mailTemplate.getName());
            throw  new ConflictException(String.format("A template with name %s exists!", mailTemplate.getName()));
        }
        return mailTemplateRepository.save(mailTemplate);
    }

    @Override
    public void update(CustomPrincipal principal, MailTemplate mailTemplate) {
        //todo validate uniaue field
        mailTemplateRepository.save(mailTemplate);
    }

    @Override
    public MailTemplate findById(CustomPrincipal principal, String id) {
        return mailTemplateRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Mail template with id {} not found", id);
                    throw new ResourceNotFoundException(String.format("Mail template with id %s not found", id));
                }
        );
    }

    @Override
    public MailTemplate findByName(CustomPrincipal principal, String name) {
        return mailTemplateRepository.findByName(name).orElseThrow(
                () -> {
                    log.error("Mail template with name {} not found", name);
                    throw new ResourceNotFoundException(String.format("Mail template with name %s not found", name));
                }
        );
    }

    @Override
    public Page<MailTemplate> findAll(CustomPrincipal principal, String name, PageRequest pageRequest) {

        Page<MailTemplate> mailTemplatePage;

        if (!StringUtils.isEmpty(name)) {
            //search by name
            mailTemplatePage = mailTemplateRepository.findByNameContains(name, pageRequest);
        }
        else{
            // search all
            mailTemplatePage = mailTemplateRepository.findAll(pageRequest);
        }

        return mailTemplatePage;
    }
}
