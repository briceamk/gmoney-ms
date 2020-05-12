package cm.g2s.partner.shared.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto {
    private String id;
    private Integer version;
    private String createdUid;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime createdDate;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ", shape=JsonFormat.Shape.STRING)
    private LocalDateTime lastModifiedDate;
    private String lastUpdatedUid;
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @Size(max = 64, message = "Last name has maximum 64 character")
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate bornDate;
    //TODO add unique constraint validator
    private String nicId;
    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate nicIssueDate;
    private String nicIssuePlace;
    private String city;
    private String country;
    private BigDecimal creditLimit;
    @NotEmpty(message = "Type is required")
    private String type;
    private String active;
    private List<WalletDto> walletDtos;
    private PartnerCategoryDto categoryDto;
}
