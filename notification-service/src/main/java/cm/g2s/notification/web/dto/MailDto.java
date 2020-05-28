package cm.g2s.notification.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailDto implements Serializable {

    static final long serialVersionUID = -2492778422982982192L;

    private String id;
    @Column(nullable = false)
    @NotEmpty(message = "initiator is required!")
    private String emailFrom;
    private String object;
    @NotEmpty(message = "recipient is required!")
    private String emailTo;
    private String emailCc;
    private String emailCci;
    @NotEmpty(message = "content is required!")
    private String content;
    private String relatedClass;
    private String relatedObjectId;
    private String reference;
    private LocalDateTime sendDate;
    private String state;
}
