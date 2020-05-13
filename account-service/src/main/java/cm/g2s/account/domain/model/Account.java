package cm.g2s.account.domain.model;

import cm.g2s.account.domain.data.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
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


}
