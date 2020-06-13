package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public  class Wallet extends BaseEntity {

    static final long serialVersionUID = 9186582349103994579L;
    
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

    @Builder
    public Wallet(String id, String name, WalletType type, Boolean isDefault, Boolean active, Partner partner) {
        super(id);
        this.name = name;
        this.type = type;
        this.isDefault = isDefault;
        this.active = active;
        this.partner = partner;
    }
}
