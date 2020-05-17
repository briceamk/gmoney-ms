package cm.g2s.partner.domain.model;

import cm.g2s.partner.domain.data.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class PartnerCategory extends BaseEntity {

    static final long serialVersionUID = -6901293121815381283L;

    @Column(nullable = false, unique = true, length = 64)
    private String name;
    private BigDecimal creditLimit;
    private Boolean active;

    @Builder
    public PartnerCategory(String id, String name, BigDecimal creditLimit, Boolean active) {
        super(id);
        this.name = name;
        this.creditLimit = creditLimit;
        this.active = active;
    }
}
