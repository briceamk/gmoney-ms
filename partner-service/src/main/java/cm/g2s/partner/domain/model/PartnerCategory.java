package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
public class PartnerCategory extends BaseEntity {

    static final long serialVersionUID = -6901293121815381283L;

    @Column(nullable = false, unique = true, length = 64)
    private String name;
    private BigDecimal creditLimit;
    private Boolean active;
    private Boolean defaultCategory;
    @Column(nullable = false)
    private String ruleId;

    @Builder
    public PartnerCategory(String id, String name, BigDecimal creditLimit, Boolean active, Boolean defaultCategory) {
        super(id);
        this.name = name;
        this.creditLimit = creditLimit;
        this.active = active;
        this.defaultCategory = defaultCategory;
    }
}
