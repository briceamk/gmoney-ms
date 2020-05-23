package cm.g2s.transaction.web.dto;

import cm.g2s.transaction.service.loan.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {

    static final long serialVersionUID = 122532156451790138L;

    private String id;
    private String number;
    private String originRef;
    private String mobile;
    private Timestamp creationDate;
    private Timestamp issueDate;
    private BigDecimal amount;
    private String mode;
    private String state;
    private String type;
    private String origin;
    private LoanDto loanDto;
    private PartnerDto partnerDto;
    private AccountDto accountDto;
    private CompanyDto companyDto;
    private UserDto userDto;
}
