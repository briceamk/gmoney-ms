package cm.g2s.notification.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplateDto implements Serializable {

    static final long serialVersionUID = -2492778422982982192L;

    private String id;
    @NotEmpty(message = "name is required!")
    private String name;
    @NotEmpty(message = "subject is required!")
    private String subject;
    @NotEmpty(message = "content is required!")
    private String content;
}
