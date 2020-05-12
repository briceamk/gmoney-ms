package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet extends BaseEntity {
    @Column(unique = true, nullable = false, length = 16)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 12)
    private WalletType type;
    private Boolean isDefault;
    private Boolean active = true;
    @ManyToOne
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;
}
