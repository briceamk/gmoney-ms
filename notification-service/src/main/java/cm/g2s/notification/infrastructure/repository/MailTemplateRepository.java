package cm.g2s.notification.infrastructure.repository;

import cm.g2s.notification.domain.model.MailTemplate;
import cm.g2s.notification.domain.model.MailTemplateType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, String> {

    Optional<MailTemplate> findByName(String name);

    Page<MailTemplate> findByNameContains(String name, Pageable pageable);

    Boolean existsByName(String name);

    Optional<MailTemplate> findByType(MailTemplateType type);
}
