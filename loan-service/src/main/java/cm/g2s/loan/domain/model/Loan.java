package cm.g2s.loan.domain.model;

import cm.g2s.loan.domain.data.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class Loan extends BaseEntity {

    @Column(length = 32, nullable = false)
    private String number;
    @Column(nullable = false)
    private String partnerId;
    @Column(nullable = false)
    private String accountId;
    private String companyId;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String mobile;
    @Column(nullable = false)
    private String ruleId;
    @Column(nullable = false)
    private Timestamp creationDate;
    @Column(nullable = false)
    private Timestamp issueDate;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal interest;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanMode mode;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanState state;
}
