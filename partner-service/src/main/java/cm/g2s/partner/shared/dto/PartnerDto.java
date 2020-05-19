package cm.g2s.partner.shared.dto;

import cm.g2s.partner.service.company.model.CompanyDto;
import cm.g2s.partner.service.uaa.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDto implements Serializable {

    static final long serialVersionUID = -7008212650086022961L;

    private String id;
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @Size(max = 64, message = "Last name has maximum 64 character")
    private String lastName;
    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    @Past(message = "you have provide a future date")
    private LocalDate bornDate;
    //TODO add unique constraint validator
    private String nicId;
    @NotEmpty(message = "email is required")
    @Email(message = "invalid email!")
    private String email;
    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    @Past(message = "you have provide a future date")
    private LocalDate nicIssueDate;
    private String nicIssuePlace;
    private String city;
    private String country;
    private CompanyDto companyDto;
    @PositiveOrZero(message = "credit limit is greater thant zeros")
    private BigDecimal creditLimit;
    private String type;
    private String state;
    @NotEmpty(message = "You mus provide a minimum of one wallet")
    private List<WalletDto> walletDtos;
    @NotNull(message = "category is required")
    private PartnerCategoryDto categoryDto;
    @NotNull(message = "user is required")
    private UserDto userDto;


}
