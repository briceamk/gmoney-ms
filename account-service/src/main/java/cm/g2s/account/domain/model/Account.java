package cm.g2s.account.domain.model;

import cm.g2s.account.domain.data.BaseEntity;
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
public class Account extends BaseEntity {
    @Column(length = 16, nullable = false, updatable = false)
    private String number;
    @Column(length = 2, nullable = false, updatable = false)
    private String key;
    @Column(nullable = false)
    private BigDecimal balance;
    private Timestamp creationDate;
    private Timestamp lastUpdateBalanceDate;
    @Column(nullable = false)
    private String partnerId;
    private String userId;
    @Enumerated(EnumType.STRING)
    private AccountState state;

    @Builder
    public Account(String id,   String number, String key, BigDecimal balance, Timestamp creationDate,
                   Timestamp lastUpdateBalanceDate, String partnerId, String userId, AccountState state) {
        super(id);
        this.number = number;
        this.key = key;
        this.balance = balance;
        this.creationDate = creationDate;
        this.lastUpdateBalanceDate = lastUpdateBalanceDate;
        this.partnerId = partnerId;
        this.userId = userId;
        this.state = state;
    }
}
