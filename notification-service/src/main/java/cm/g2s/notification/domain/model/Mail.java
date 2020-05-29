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
public class Mail extends BaseEntity {

    static final long serialVersionUID = 3913125620645474768L;

    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String emailTo;
    private String emailCc;
    private String emailCci;
    @Column(columnDefinition = "text")
    private String content;
    private String relatedClass;
    private String relatedObjectId;
    private String reference;
    private Timestamp sendDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MailState state;

    @Builder
    public Mail(String id, String subject, String emailTo, String emailCc, String emailCci, String content,
                String relatedClass, String relatedObjectId, String reference, Timestamp sendDate, MailState state) {
        super(id);
        this.subject = subject;
        this.emailTo = emailTo;
        this.emailCc = emailCc;
        this.emailCci = emailCci;
        this.content = content;
        this.relatedClass = relatedClass;
        this.relatedObjectId = relatedObjectId;
        this.reference = reference;
        this.sendDate = sendDate;
        this.state = state;
    }
}
