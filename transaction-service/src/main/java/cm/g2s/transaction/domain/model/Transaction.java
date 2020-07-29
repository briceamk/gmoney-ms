package cm.g2s.transaction.domain.model;

import cm.g2s.transaction.domain.data.BaseEntity;
import cm.g2s.transaction.service.momo.dto.TransferState;
import lombok.Builder;
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
public class Transaction extends BaseEntity {

    static final long serialVersionUID = 219923127861921743L;

    @Column(length = 32, nullable = false, updatable = false)
    private String number;
    @Column(nullable = false)
    private String originRef;
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
    private Timestamp creationDate;
    @Column(nullable = false)
    private Timestamp issueDate;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionMode mode;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionState state;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionOrigin origin;
    private String loanId;
    //private String refundId;
    //private String penaltyId;
    private String financialTransactionId;
    private String errorCode;
    private String errorMessage;
    @Enumerated(EnumType.STRING)
    private TransferState transferState;


    @Builder
    public Transaction(String id, String number, String originRef, String partnerId,
                       String accountId, String companyId, String userId, String mobile,
                Timestamp creationDate, Timestamp issueDate, BigDecimal amount,
                TransactionMode mode, TransactionState state, TransactionType type,
                TransactionOrigin origin, String loanId, String financialTransactionId,
            String errorCode, String errorMessage, TransferState transferState/*,String refundId, String penaltyId*/) {
        super(id);
        this.number = number;
        this.originRef = originRef;
        this.partnerId = partnerId;
        this.accountId = accountId;
        this.companyId = companyId;
        this.userId = userId;
        this.mobile = mobile;
        this.creationDate = creationDate;
        this.issueDate = issueDate;
        this.amount = amount;
        this.mode = mode;
        this.state = state;
        this.type = type;
        this.origin = origin;
        this.loanId = loanId;
        this.financialTransactionId = financialTransactionId;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.transferState = transferState;
        //this.refundId = refundId;
        //this.penaltyId = penaltyId;
    }
}
