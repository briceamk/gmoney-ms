package cm.g2s.notification.domain.model;

import cm.g2s.notification.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class MailTemplate extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String subject;
    @Column(nullable = false, columnDefinition = "text")
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private MailTemplateType type;

    @Builder
    public MailTemplate(String id, String name, String subject, String content, MailTemplateType type) {
        super(id);
        this.name = name;
        this.subject = subject;
        this.content = content;
        this.type = type;
    }
}
