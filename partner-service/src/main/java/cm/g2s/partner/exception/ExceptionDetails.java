package cm.g2s.partner.exception;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExceptionDetails {
    private Integer status;
    private LocalDateTime timestamp;
    private String message;
    private String detail;
}
