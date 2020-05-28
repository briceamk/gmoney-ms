package cm.g2s.notification.infrastructure.repository;

import cm.g2s.notification.domain.model.Mail;
import cm.g2s.notification.domain.model.MailState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailRepository extends JpaRepository<Mail, String> {

    Page<Mail> findByReference(String reference, Pageable pageable);

    Page<Mail> findByState(MailState valueOf, Pageable pageable);

    List<Mail> findByStateNot(MailState state);
}
